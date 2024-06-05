# Code Python permettant la generations d'un graphe en liant le chemin d'une commune A vers une commune B.
# Le programme JavaFX envoie une List de d'Ids de communes au code Python, le code python ensuite
# lui trace les arrêtes de toutes les communes puis renvoie une image au code JavaFX.


from flask import Flask, request, send_file
import matplotlib.pyplot as plt
import networkx as nx
import pandas as pd
import ast

app = Flask(__name__)

# Importation du fichier CSV comme un panda dataframe
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

    # Positionnement des nœuds pour un espacement des noms de villes
    pos = {node: (x, y) for node, (x, y) in pos_insee.items()}
    pos_labels = {k: (x, y + 0.01) for k, (x, y) in pos.items()}

    # Créer un sous-ensemble de labels pour n'inclure que les communes dans le chemin ou les communes de départ et d'arrivée
    subset_labels = {com: label_insee[com] for com in G.nodes if com in connections or com == connections[0] or com == connections[-1]}
    
    nx.draw(G, pos=pos, node_size=10, alpha=0.4, edge_color="gray", font_size=10)
    nx.draw_networkx_edges(G, pos=pos, edgelist=red_edges, edge_color='red', width=2)
    nx.draw_networkx_labels(G, pos=pos_labels, labels=subset_labels, font_size=15, font_weight="bold")

    # Trouver les limites des axes pour zoomer sur le chemin
    min_x = min(pos[node][0] for node in connections)
    max_x = max(pos[node][0] for node in connections)
    min_y = min(pos[node][1] for node in connections)
    max_y = max(pos[node][1] for node in connections)

    # Ajouter une marge autour des limites des axes
    margin = 0.1
    plt.xlim(min_x - margin, max_x + margin)
    plt.ylim(min_y - margin, max_y + margin)

    filename = "bretagne_graph_with_paths.png"
    plt.savefig(filename, dpi=300)
    plt.close()

    return send_file(filename, mimetype='image/png')


if __name__ == '__main__':
    app.run(host='192.168.1.24', port=5000)