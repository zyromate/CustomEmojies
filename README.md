# 🎭 CustomEmojis  

A lightweight **Minecraft Spigot plugin** that brings customizable chat emojis and an interactive GUI menu for players.  

---

## ✨ Features  

- 🔑 **Permission-based emojis** → Only players with `customemojis.use` (Configurable) can use emojis in chat.  
- ⚡ **Configurable emojis** → Define replacements directly in `config.yml` (`:smile: -> 😄`).  
- 📖 **Emoji GUI** → Browse emojis with a fully paginated GUI (`/emojis`).  
- 🛠️ **Reload support** → Reload config instantly with `/customemojis reload`.  
- 🎨 **Customizable GUI elements** → Edit borders, lore, buttons, and names in the config.  
- ✅ **Optimized performance** → Efficient chat parsing and smooth GUI navigation.  

---

## 📦 Installation  

1. Download the plugin JAR file.  
2. Drop it into your server’s `plugins/` folder.  
3. Start (or restart) your server.  
4. Configure emojis inside `config.yml`.  
5. Reload with `/customemojis reload`.

---

## ⌨️ Commands  

| Command                | Description                              | Permission              |
|-------------------------|------------------------------------------|--------------------------|
| `/customemojis reload` | Reloads the configuration.               | `customemojis.reload`   |
| `/emojis`              | Opens the emoji GUI with all available.  | `customemojis.use`      |



## 📜 Permissions

| Permission              | Description                           | Extra             |
|--------------------------|---------------------------------------|-------------------|
| `customemojis.use`      | Allows players to use emojis in chat. | Configurable      |
| `customemojis.reload`   | Allows reloading the configuration.   | ----------------- |

---
## ⚙️ Default Configuration (`config.yml`)  

```yaml
CustomEmojis:
  command-usage:
    - "&6_____________.[ &2Custom Emojis &6]._____________"
    - "&b/CustomEmojis reload &eReloads the configuration"
    - "&b/emojis &eOpens the gui with all the emojis"

  reload-success: "&7Custom Emojis has been reloaded &asuccessfully"

  Gui:
    open-message: "&aOpening Emojis GUI"
    # NOT FUNCTIONAL:
    Title: "Emojis (Page {page}/{totalPages})"
    BorderItemName: " "
    EmojiItem:
      Name: "Emoji: {emojiName}"
      Lore:
        - "Before: {emojiName}"
        - "After: {emojiReplaced}"
    PreviousPageButton:
      Name: "Previous Page"
    NextPageButton:
      Name: "Next Page"
    HeaderItem:
      Name: "Emoji List"
      Lore:
        - "Browse through the emoji list!"
        - "Page {page} of {totalPages}"

Emojis:
  - ":skull:->&4☠&r"
  - "<3->&c❤&r"
  - ":star:->&6✮&r"
  - ":yes:->&a✔&r"
  - ":no:->&c✖&r"
  - ":java:->&6☕&r"
  - ":arrow:->&e➜&r"
  - ":shrug:->&e¯\\_(ツ)_/¯&r"
  - ":tableflip:->&c(╯°□°）╯&7 ┻━┻&r"
  - "o/->&d( ﾟ◡ﾟ)/&r"
  - ":123:->&a1&e2&c3&"
  - ":totem:->&b☉&e_&b☉&r"
  - ":typing:->&e✎&6...&r"
  - ":maths:->&a√&e(&aπ+x&e)&a=&cL&"
  - ":snail:->&e@&a'&e-&a'&r"
  - ":thinking:->&6(&a0&6.&ao&c?&6)&r"
  - ":gimme:->&b༼つ◕_◕༽つ&r"
  - ":wizard:->&e(&b'&e-&b'&e)⊃━&c☆ﾟ.&d*･｡ﾟ&"
  - ":pvp:->&e⚔&r"
  - ":peace:->&b✌&r"
  - ":oof:->&c&lOOF&r"
  - ":puffer:->&e<('O')>&r"
  - ":yey:->&aヽ (◕◡◕) ﾉ&r"
  - ":cat:->&e= &b＾● ⋏ ●＾ &e=&r"
  - ":dab:->&d<&eo&d/&r"
  - ":dj:->&9ヽ&5(&d⌐&c■&6_&e■^b)&3ノ&9♬&r"
  - ":snow:->&b☃&r"
  - "^_^->&a^_^&r"
  - "h/->&eヽ(^◇^*)/&r"
  - "^-^->&a^-^&r"
  - ":sloth:->&6(&8・&6⊝&8・&6)&r"
  - ":cute:->&e(&a✿&e◠‿◠)&r"
  - ":dog:->&6(ᵔᴥᵔ)&r"
```
---
## 📸 Preview  

<img width="399" height="36" alt="image" src="https://github.com/user-attachments/assets/87598d01-dade-4174-9210-b072b829e2d3" />

🟢 **Emoji GUI**  
- Paginated menu with emojis as items.  
- Navigation buttons (`Next Page` / `Previous Page`).  
- Configurable header & border design.  

![CustomEmojis Preview](https://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExMWxkOHN0azE2eng5MndiZmVtNTNuMWgxeG1yaG4xMXkzOTQydWoxMCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/fhCTqVOwGwh4dpaPeq/giphy.gif)

---

## 🛠️ Support  

- Works on **Spigot / Paper** servers.  
- Requires **Minecraft 1.8+**.
- Need support? Discord: Zyromate
