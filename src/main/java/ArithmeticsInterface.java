package main.java;

//testowe, interfejs jeszcze nie dziala - na razie castuje wszystko na double
//nie usuwam tego z nadzieją, że uda się to później jednak wdrożyć aby uprościć kod
interface ArithmeticsInterface<T extends Number> {
    T zero(); // Adding zero items
    T add(T lhs, T rhs);
    T multiply(T lhs, T rhs);
}
