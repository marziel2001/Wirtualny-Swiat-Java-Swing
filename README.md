# Wirtualny Świat – Java Swing

A turn-based life simulation written in Java, featuring a graphical grid where animals and plants interact each round.

**Author:** Marcel Zielinski – 191005

---

## Technology

| Area | Details |
|------|---------|
| Language | Java (JDK 8+) |
| GUI framework | Java Swing (`JFrame`, `JPanel`, `JButton`, `JMenuBar`, …) |
| Build system | None required – compile & run with standard `javac` / `java` |
| Persistence | Plain-text `.txt` files (save / load) |

---

## Architecture

The project follows an **object-oriented, layered** design inside the `po2proj` package:

```
po2proj/
├── Main.java               – entry point, creates UI
├── UI.java                 – Swing GUI: window, menu, keyboard input, repaint
├── Swiat.java              – world state: grid, organism list, turn logic, save/load
├── Organizm.java           – abstract base for every living thing
├── Zwierze.java            – abstract animal (movement, combat, reproduction)
├── Roslina.java            – abstract plant (spreading)
├── Punkt.java              – 2-D coordinate helper
├── KreatorOrganizmow.java  – factory that instantiates any organism by enum type
├── zwierzeta/              – concrete animals
│   ├── Czlowiek.java       – player-controlled human with an invincibility skill
│   ├── Wilk.java
│   ├── Owca.java
│   ├── Lis.java
│   ├── Zolw.java
│   └── Antylopa.java       – can flee from attackers, jumps 2 squares
└── rosliny/                – concrete plants
    ├── Trawa.java
    ├── Mlecz.java
    ├── Guarana.java        – boosts animal strength on consumption
    ├── WilczeJagody.java   – kills animals that eat them
    └── BarszczSosnowskiego.java – kills animals on contact
```

### Key design patterns
- **Template Method** – `Organizm` defines the interface (`Akcja`, `Kolizja`); `Zwierze` and `Roslina` implement shared behaviour; concrete classes override only what differs.
- **Factory** – `KreatorOrganizmow.StworzNowyOrganizm()` centralises construction so the world and save/load code never depend on concrete types.
- **Turn ordering** – organisms are sorted each turn by *initiative* (descending), then by *birth turn* (ascending, i.e. older organisms act first on a tie).

---

## Organisms

### Animals
| Organism | Strength | Initiative | Special behaviour |
|----------|----------|------------|-------------------|
| Człowiek (Human) | 5 | 4 | Player-controlled; press **U** to activate 5-turn invincibility (10-turn cooldown) |
| Wilk (Wolf) | 9 | 5 | Standard predator |
| Owca (Sheep) | 4 | 4 | Standard herbivore |
| Lis (Fox) | 3 | 7 | Avoids stronger organisms |
| Żółw (Turtle) | 2 | 1 | 75 % chance to deflect an attack |
| Antylopa (Antelope) | 4 | 4 | Moves 2 squares; 50 % chance to flee attackers |

### Plants
| Plant | Special behaviour |
|-------|-------------------|
| Trawa (Grass) | Spreads normally |
| Mlecz (Dandelion) | Spreads three times per turn |
| Guarana | Increases the eating animal's strength by 3 |
| Wilcze Jagody (Wolfberries) | Kills the animal that eats them |
| Barszcz Sosnowskiego (Hogweed) | Kills adjacent animals |

---

## How to Run

1. **Compile** all sources (from the project root):
   ```bash
   javac -d out $(find . -name "*.java")
   ```
2. **Run:**
   ```bash
   java -cp out po2proj.Main
   ```

A window titled **Wirtualny Swiat** will open.

---

## How to Play

### Starting / loading a game
| Action | How |
|--------|-----|
| New game | **Menu → Nowa Gra** → enter board width and height |
| Load saved game | **Menu → Wczytaj** → enter the filename (without `.txt`) |
| Save current game | **Menu → Zapisz** → enter the filename (without `.txt`) |
| Quit | **Menu → Wyjscie** |

### Controls (during a game)
| Key | Action |
|-----|--------|
| **Arrow keys** | Move the human one square |
| **U** | Activate the human's invincibility skill |
| **Space** | Advance one turn without moving the human |

### Board interaction
- **Empty squares** are clickable – clicking one opens a list so you can manually place any organism there.
- Each organism type has a **unique colour** shown in the legend strip beneath the board.
- The **log panel** below the legend shows every event that happened during the last turn.

---

## Save File Format

Each save is a plain `.txt` file:

```
<width> <height> <turn> <humanAlive> <gameOver>
<TYPE> <x> <y> <strength> <birthTurn> <alive>
...
```

One organism per line; the world is fully reconstructed on load.
