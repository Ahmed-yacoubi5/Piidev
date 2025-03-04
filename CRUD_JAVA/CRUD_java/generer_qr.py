import qrcode
import sys
import os

def generer_qr_code(nom, type_absence, date_debut, date_fin):
    """ Génère un QR Code contenant les informations de l'absence et l'enregistre comme image. """

    # 📌 Contenu du QR Code
    contenu_qr = f"Employé: {nom}\nType: {type_absence}\nDébut: {date_debut}\nFin: {date_fin}"

    # 📷 Générer le QR Code
    qr = qrcode.make(contenu_qr)
    qr_path = f"qr_{nom}.png"
    qr.save(qr_path)

    print(f"✅ QR Code généré avec succès : {qr_path}")
    return qr_path

# 🛠 Lire les arguments envoyés par Java
if __name__ == "__main__":
    if len(sys.argv) < 5:
        print("❌ Erreur : Arguments manquants")
        sys.exit(1)

    nom = sys.argv[1]
    type_absence = sys.argv[2]
    date_debut = sys.argv[3]
    date_fin = sys.argv[4]

    # Génération du QR Code
    generer_qr_code(nom, type_absence, date_debut, date_fin)
