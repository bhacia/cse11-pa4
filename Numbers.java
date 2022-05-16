import tester.*;

interface Number {
    int numerator();
    int denominator();
    Number add(Number other);
    Number multiply(Number other);
    String toText();
    double toDouble();
}

class WholeNumber implements Number {
    int n;
    WholeNumber(int n) {
        this.n = n;
    }

    public int numerator() {
        return this.n;
    }
    public int denominator() {
        return 1;
    }
    public Number add(Number other) {
        return new Fraction((this.n * other.denominator()) + other.numerator(), other.denominator());
    }
    public Number multiply(Number other) {
        return new Fraction((this.n * other.numerator()), other.denominator());
    }
    public String toText() {
        return Integer.toString(this.n);
    }
    public double toDouble() {
        double intToDouble = this.n;
        return intToDouble;
    }
}

class Fraction implements Number {
    int n;
    int d;
    Fraction(int n, int d) {
        this.n = n;
        this.d = d;
    }

    public int numerator() {
        return this.n;
    }
    public int denominator() {
        return this.d;
    }
    public Number add(Number other) {
        return new Fraction((this.n * other.denominator()) + (other.numerator() * this.d), (this.d * other.denominator()));
    }
    public Number multiply(Number other) {
        return new Fraction(this.n * other.numerator(), this.d * other.denominator()); 
    }
    public String toText() {
        String nString = Integer.toString(this.n);
        String dString = Integer.toString(this.d);
        return nString + "/" + dString;
    }
    public double toDouble() {
        double nToDouble = this.n;
        double dToDouble = this.d;
        return nToDouble / dToDouble;
    }
}

class Numbers {
    Number n1 = new WholeNumber(5);
    Number n2 = new WholeNumber(7);
    Number n3 = new Fraction(7, 2);
    Number n4 = new Fraction(1, 2);

    void testAdd(Tester t) {
        t.checkExpect(this.n1.add(this.n2).toDouble(), 12.0);
        t.checkExpect(this.n1.add(this.n3).toDouble(), 5 + 7.0/2.0);
        t.checkExpect(this.n3.add(this.n3).toDouble(), 7.0);
    }

    void testMultiply(Tester t) {
        t.checkExpect(this.n1.multiply(this.n4).toDouble(), 2.5);
        t.checkExpect(this.n3.multiply(this.n4).toDouble(), 7.0/4.0);
    }

    void testNumDem(Tester t) {
        t.checkExpect(this.n3.numerator(), 7);
        t.checkExpect(this.n1.numerator(), 5);
        t.checkExpect(this.n4.denominator(), 2);
        t.checkExpect(this.n2.denominator(), 1);
    }

    void testToString(Tester t) {
        t.checkExpect(this.n4.toText(), "1/2");
        t.checkExpect(this.n3.toText(), "7/2");
        t.checkExpect(this.n2.toText(), "7");
    }
}

//Exploration
class ExamplesNumbers {
    double result1 = 0.1 + 0.2 + 0.3; //0.6000000000000001
    double result2 = 0.1 + (0.2 + 0.3); //0.6

    Number num1 = new Fraction(1, 10);
    Number num2 = new Fraction(2, 10);
    Number num3 = new Fraction(3, 10);

    Number result1pt1 = this.num1.add(this.num2);
    Number result1pt2 = this.result1pt1.add(this.num3);
    Number result2pt1 = this.num2.add(this.num3);
    Number result2pt2 = this.result2pt1.add(this.num1);

    void testToString(Tester t) {
        t.checkExpect(this.result1pt2.toText(), "600/1000");
        t.checkExpect(this.result2pt2.toText(), "600/1000");
    }
}