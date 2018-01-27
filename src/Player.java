public class Player {

    private String name;
    private int bombs;
    private int snippets;

    public Player(String playerName) {
        this.name = playerName;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public void setSnippets(int snippets) {
        this.snippets = snippets;
    }

    public int getBombs() {
        return this.bombs;
    }
}
