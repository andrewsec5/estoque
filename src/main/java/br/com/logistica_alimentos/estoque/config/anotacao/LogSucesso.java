package br.com.logistica_alimentos.estoque.config.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogSucesso {

    String mensagem() default "";
    String origem() default "SERVICO_ESTOQUE";

}
