package fr.layer4.payment4j;

public interface ExceptionResolver {

	Throwable resolve(Throwable throwable);
}
