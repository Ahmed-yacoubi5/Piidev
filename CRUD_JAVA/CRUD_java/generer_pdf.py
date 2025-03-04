from reportlab.lib.pagesizes import A4
from reportlab.pdfgen import canvas
from reportlab.lib import colors
import sys
import os

def generer_pdf(nom, type_absence, date_debut, date_fin):
    """ Génère un fichier PDF avec un design avancé, sans QR Code intégré. """
    
    # 📄 Définition du nom du fichier PDF
    fichier_pdf = f"absence_{nom}.pdf"

    # 🎨 Définition du canvas avec une page A4
    c = canvas.Canvas(fichier_pdf, pagesize=A4)
    largeur, hauteur = A4

    # 🔹 Titre du document avec une couleur personnalisée
    c.setFont("Helvetica-Bold", 22)
    c.setFillColor(colors.darkblue)
    c.drawString(180, hauteur - 80, "Justification d'Absence")

    # 📌 Cadre contenant les détails
    cadre_x, cadre_y = 50, hauteur - 300  # Position du cadre
    cadre_largeur, cadre_hauteur = 500, 200  # Taille du cadre

    c.setStrokeColor(colors.black)
    c.setLineWidth(2)
    c.rect(cadre_x, cadre_y, cadre_largeur, cadre_hauteur, stroke=1, fill=0)

    # 📜 Texte des informations (avec alignement et espacement)
    c.setFont("Helvetica", 14)
    c.setFillColor(colors.black)
    c.drawString(70, hauteur - 120, f"• Employé : {nom}")
    c.drawString(70, hauteur - 140, f"• Type d'absence : {type_absence}")
    c.drawString(70, hauteur - 160, f"• Date début : {date_debut}")
    c.drawString(70, hauteur - 180, f"• Date fin : {date_fin}")

    # ✍ Signature de validation
    c.setFont("Times-Italic", 12)
    c.setFillColor(colors.gray)
    c.drawString(70, hauteur - 320, "_________________________")
    c.drawString(70, hauteur - 340, "Signature de l'administration")

    # 📝 Ajouter un pied de page
    c.setFont("Helvetica", 10)
    c.setFillColor(colors.gray)
    c.drawString(50, 40, "Document généré automatiquement - Ne nécessite pas de signature manuscrite")

    # 📄 Sauvegarde du fichier PDF
    c.save()

    print(f"📄 PDF généré avec succès : {fichier_pdf}")
    return fichier_pdf

# 🛠 Lire les arguments envoyés par Java
if __name__ == "__main__":
    if len(sys.argv) < 5:
        print("❌ Erreur : Arguments manquants")
        sys.exit(1)

    nom = sys.argv[1]
    type_absence = sys.argv[2]
    date_debut = sys.argv[3]
    date_fin = sys.argv[4]

    # Génération du PDF
    generer_pdf(nom, type_absence, date_debut, date_fin)
