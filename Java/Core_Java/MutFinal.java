public class MutFinal {
    private final StringBuilder eval = new StringBuilder();

    public void append(String s) {
        eval.append(s);
    }

    public String eval() {
        return (String) eval.toString();
    }
}
