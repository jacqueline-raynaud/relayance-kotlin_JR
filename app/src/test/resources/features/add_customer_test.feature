#language: fr
Fonctionnalité: Ajout d'un client
  Afin d'enregistrer un nouveau client dans le CRM
  En tant qu'utilisateur
  Je veux remplir un formulaire et valider l'ajout

  Scénario: Ajout réussi avec un email valide
    Soit un formulaire d'ajout de client vide
    Quand je saisis le nom "Jean Dupont" et l'email "jean.dupont@example.com"
    Et je valide l'ajout
    Alors le client "Jean Dupont" est présent dans la liste des clients
    Et aucune erreur d'email n'est affichée

  Scénario: Ajout refusé avec un email au format invalide
    Soit un formulaire d'ajout de client vide
    Quand je saisis le nom "Marie Martin" et l'email "marie.martin.example.com"
    Et je valide l'ajout
    Alors une erreur d'email est affichée
    Et la liste des clients reste inchangée