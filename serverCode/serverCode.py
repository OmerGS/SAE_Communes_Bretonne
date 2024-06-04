from flask import Flask, request, send_file
import matplotlib.pyplot as plt
import networkx as nx
import pandas as pd
import ast

app = Flask(__name__)

# importation du fichier csv comme un panda dataframe
communes = pd.read_csv("./voisinageCommunesBretonnes.csv", sep=';')

def amezek(com):
    voisins = [int(num) for num in communes['insee_voisins'][com].split('|')]
    amezek = []
    for vois in voisins:
        if (22000 <= vois < 23000) or (29000 <= vois < 30000) or (35000 <= vois < 36000) or (56000 <= vois < 57000):
            if vois != communes['insee'][com]:
                amezek.append(vois)
    return amezek

voisins_dict = {communes['insee'][x]: amezek(x) for x in range(len(communes['insee']))}
G = nx.from_dict_of_lists(voisins_dict)
geo = pd.read_csv("./communes-geo.csv", sep=';')
geo['Latitude'] = geo['Geo Point'].apply(lambda x: ast.literal_eval(x)[0])
geo['Longitude'] = geo['Geo Point'].apply(lambda x: ast.literal_eval(x)[1])

def pos_insee(G, data):
    pos = {}
    for com in G.nodes:
        y = float(data[data['Code Officiel Commune'] == com]['Latitude'].iloc[0])
        x = float(data[data['Code Officiel Commune'] == com]['Longitude'].iloc[0])
        pos[com] = [x, y]
    return pos

pos_insee = pos_insee(G, geo)

def label_insee(G, data):
    label = {}
    for com in G.nodes:
        lab = data[data['Code Officiel Commune'] == com]['Nom Officiel Commune'].iloc[0]
        label[com] = lab
    return label

label_insee = label_insee(G, geo)

def create_path(connections):
    edges = []
    for i in range(len(connections) - 1):
        edges.append((connections[i], connections[i + 1]))
    return edges

@app.route('/plot_graph', methods=['POST'])
def plot_graph():
    data = request.json
    connections = data['connections']
    red_edges = create_path(connections)

    plt.figure(figsize=(20, 15))
    nx.draw(G, pos=pos_insee, node_size=10, alpha=0.4, edge_color="gray", font_size=10, labels=label_insee)
    nx.draw_networkx_edges(G, pos=pos_insee, edgelist=red_edges, edge_color='red', width=2)

    filename = "bretagne_graph_with_paths.png"
    plt.savefig(filename, dpi=300)
    plt.close()

    return send_file(filename, mimetype='image/png')

if __name__ == '__main__':
    app.run(host='192.168.1.24', port=5000)