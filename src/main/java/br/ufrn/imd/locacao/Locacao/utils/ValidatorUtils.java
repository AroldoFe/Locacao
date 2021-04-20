package br.ufrn.imd.locacao.Locacao.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class ValidatorUtils {
    /**
     * Valida se a string é diferente de null e não vazia
     */
    public static boolean isEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * Valida se um objeto está vazio
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return isEmpty((String) o);
        }
        if (o instanceof Number) {
            Number i = (Number) o;
            if (i instanceof Double || i instanceof BigDecimal) {
                return (i.doubleValue() == 0.0);
            }
            return (i.intValue() == 0);
        }
        if (o instanceof Object[]) {
            return ((Object[]) o).length == 0;
        }
        if (o instanceof int[]) {
            return ((int[]) o).length == 0;
        }
        if (o instanceof Collection<?>) {
            return ((Collection<?>) o).size() == 0;
        }
        if (o instanceof Map<?, ?>) {
            return ((Map<?, ?>) o).size() == 0;
        }
        return false;
    }

    /**
     * Valida se um objeto não está vazio
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * Valida se todos os objetos estão vazios
     */
    public static boolean isAllEmpty(Object... objects) {
        for (Object o : objects) {
            if (ValidatorUtils.isNotEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Valida se todos os objetos não estão vazios
     */
    public static boolean isAllNotEmpty(Object... objects) {
        for (Object o : objects) {
            if (ValidatorUtils.isEmpty(o)) {
                return false;
            }
        }
        return true;
    }
}
