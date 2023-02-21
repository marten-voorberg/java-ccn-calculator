public class Example {
    int doSomething(int x) {
        if (x % 2 == 0) {
            x++;
        } else if (x % 3 == 0) {
            x--;
        } else {
            x += 100;
        }

        return x;
    }

    public int doSomethingElse() {
        if (a) {
            x += 1000;
            x += 1000;
        } else if (b) {
            x += 1000;
            x += 1000;
            x += 1000;
        } else if (c) {
            x += 1000;
        } else if (d) {
            return 1;
        }

        return 100;
    }
}