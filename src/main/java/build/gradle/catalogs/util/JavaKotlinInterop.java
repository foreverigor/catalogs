package build.gradle.catalogs.util;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import java.util.function.Consumer;

public interface JavaKotlinInterop {

    static <T> Function1<? super T, Unit> toKotlinFunction(Consumer<T> consumer) {
        return (Function1<T, Unit>) t -> {
            consumer.accept(t);
            return Unit.INSTANCE;
        };
    }

    static <T> Consumer<T> toJavaConsumer(Function1<? super T, Unit> kFunc) {
        return kFunc::invoke;
    }

} // interface JavaKotlinInterop
