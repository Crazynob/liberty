package red.fuyun.liberty.proxyee;


@FunctionalInterface
public interface Cell<T, U, R,D> {

    boolean apply(T t,U u,R r,D d);


}
