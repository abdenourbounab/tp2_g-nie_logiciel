package theatricalplays;

public class Play {
  public enum PlayType {
    tragedy,
    comedy,
    history,
    pastoral
  }

  public String name;
  public PlayType type;

  // Private constructor
  private Play(String name, PlayType type) {
    this.name = name;
    this.type = type;
  }

  // Static factory method to create Play objects
  public static Play createPlay(String name, PlayType type) {
    return new Play(name, type);
  }
}
