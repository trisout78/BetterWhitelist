# BetterWhitelist

BetterWhitelist est un plugin pour Minecraft qui permet de gérer une liste blanche (whitelist) compatible avec les serveurs crackés en se basant sur un fichier de liste blanche et en vérifiant le nom d'hôte.

## Fonctionnalités

- Gestion de la whitelist par fichier
- Vérification du nom d'hôte pour les serveurs crackés
- Messages de connexion et déconnexion personnalisés
- Commande pour ajouter des joueurs à la whitelist avec gestion de cooldown

## Installation

1. Téléchargez le fichier de la dernière version stable sur [GitHub Stable Actions](https://github.com/trisout78/BetterWhitelist/actions?query=branch%3Amaster).
2. Placez le fichier téléchargé dans le dossier `plugins` de votre serveur Minecraft.
3. Démarrez ou redémarrez votre serveur pour que le plugin soit chargé.

## Configuration

Le fichier de configuration `config.yml` permet de personnaliser le comportement du plugin. Voici un exemple de configuration par défaut :

```yaml
whitelist:
  - Trisout78
  - Trisout
  - Notch
# Configuration de la whitelist par nom d'hôte
hostname-enabled: true
warning: '&cVous avez utilisé la mauvaise adresse IP, veuillez utiliser &3example.com &cinstead'
ignore-case: true
block-legacy: true
allowed-host-names:
  - 'localhost'
custom-join-leave-message: true
# Commande whitelist
whitelist-command-enabled: true
cooldown: 60 # délai en secondes
```

## Utilisation

### Commandes

- `/wl <player>` : Ajoute un joueur à la whitelist. Nécessite la permission `whitelist.add`.

### Permissions

- `whitelist.add` : Permet d'ajouter des joueurs à la whitelist.

## Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Crée une branche pour ta fonctionnalité (`git checkout -b feature/ma-fonctionnalite`)
3. Commits tes modifications (`git commit -m 'Ajout de ma fonctionnalité'`)
4. Push sur la branche (`git push origin feature/ma-fonctionnalite`)
5. Ouvre une Pull Request

## Licence

Ce projet est sous licence **GNU General Public License v3.0**. Consultez le fichier [LICENSE](LICENSE) pour plus d'informations.
