package cn.ac.yhao.check;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.BiFunction;

/**
 * 参数校验 切面
 */
@Aspect
@Component
public class CheckParamAspect {

    private static final Logger log = LoggerFactory.getLogger(CheckParamAspect.class);

    @Pointcut("@annotation(cn.ac.yhao.check.Check)")
    public void checkParam(){}

    @Around("checkParam()")  // 自定义注解的路径
    public Object check(ProceedingJoinPoint point) throws Throwable {
        Object obj = null;
        //参数校验
        String msg = doCheck(point);
        if (StringUtils.isNotBlank(msg)) {
            // 这里可以返回自己封装的返回类，
            throw new IllegalArgumentException(msg);
        }
        // 通过校验，继续执行原有方法
        obj = point.proceed();
        return obj;
    }

    /**
     *  参数校验
     * @param point ProceedingJoinPoint
     * @return 错误信息
     */
    private String doCheck(ProceedingJoinPoint point) {
        //获取方法参数值
        Object[] args = point.getArgs();
        //获取方法
        Method method = getMethod(point);
        //默认的错误信息
        String methodInfo = StringUtils.isEmpty(method.getName()) ? "" : " while celling " + method.getName();
        String msg = "";
        if (isCheck(method, args)) {
            Check annotation = method.getAnnotation(Check.class);
            String[] fields = annotation.value();
            //只支持对第一个参数校验
            Object vo = args[0];
            if (vo == null) {
                msg = "param can not be null";
            } else {
                for (String field : fields) {
                    //解析字段
                    FieldInfo info = resolveField(field, methodInfo);
                    //获取字段的值
                    Object value = ReflectionUtil.invokeGetter(vo, info.field);
                    // 执行校验规则
                    Boolean isValid = info.optEnum.fun.apply(value, info.operatorNum);
                    msg = isValid ? msg : info.innerMsg;
                }
            }
        }
        return msg;
    }

    /**
     * 解析字段
     *
     * @param field 字段字符串
     * @param methodInfo 方法信息
     * @return 字段信息实体类
     */
    private FieldInfo resolveField(String field, String methodInfo) {
        FieldInfo fieldInfo = new FieldInfo();
        String innerMsg = "";
        // 解析提示信息
        if (field.contains(SEPARATOR)) {
            String[] split = field.split(SEPARATOR);
            field = split[0];
            innerMsg = split[1];
        }
        //解析操作符
        if (field.contains(Operator.GREATER_THAN.value)) {
            fieldInfo.optEnum = Operator.GREATER_THAN;
        } else if (field.contains(Operator.GREATER_THAN_EQUAL.value)) {
            fieldInfo.optEnum = Operator.GREATER_THAN_EQUAL;
        } else if (field.contains(Operator.LESS_THAN.value)) {
            fieldInfo.optEnum = Operator.LESS_THAN;
        } else if (field.contains(Operator.LESS_THAN_EQUAL.value)) {
            fieldInfo.optEnum = Operator.LESS_THAN_EQUAL;
        } else if (field.contains(Operator.NOT_EQUAL.value)) {
            fieldInfo.optEnum = Operator.NOT_EQUAL;
        } else {
            fieldInfo.optEnum = Operator.NOT_NULL;
        }
        //不等于空，直接赋值字段
        if (fieldInfo.optEnum == Operator.NOT_NULL) {
            fieldInfo.field = field;
            fieldInfo.operatorNum = "";
        } else { //其他操作符，需要分离出字段和操作数
            fieldInfo.field = field.split(fieldInfo.optEnum.value)[0];
            fieldInfo.operatorNum = field.split(fieldInfo.optEnum.value)[1];
        }
        fieldInfo.operator = fieldInfo.optEnum.value;
        //处理错误信息
        String defaultMsg = fieldInfo.field + " must " + fieldInfo.operator + " " + fieldInfo.operatorNum + methodInfo;
        fieldInfo.innerMsg = StringUtils.isEmpty(innerMsg) ? defaultMsg : innerMsg;
        return fieldInfo;
    }

    //-------- 对不同类型的值进行校验 -------

    /**
     * 是否不为空
     *
     * @param value  字段值
     * @param operatorNum 操作数
     * @return 是否不为空
     */
    private static Boolean isNotNull(Object value, String operatorNum) {
        Boolean isNotNull = Boolean.TRUE;
        Boolean isStringNUll = (value instanceof String) && StringUtils.isEmpty((String) value);
        Boolean isCollectionNull = (value instanceof Collection) && CollectionUtils.isEmpty((Collection<?>) value);
        if (value == null) {
            isNotNull = Boolean.FALSE;
        } else if (isStringNUll || isCollectionNull){
            isNotNull = Boolean.FALSE;
        }
        return isNotNull;
    }

    /**
     * 是否大于
     *
     * @param value 字段值
     * @param operatorNum 操作数
     * @return 是否大于
     */
    private static Boolean isGreaterThan(Object value, String operatorNum) {
        Boolean isGreaterThan = Boolean.FALSE;
        if (value == null) {
            isGreaterThan = Boolean.FALSE;
        }
        Boolean isStringGreaterThan = (value instanceof String) && ((String) value).length() > Integer.valueOf(operatorNum);
        Boolean isLongGreaterThan = (value instanceof Long) && ((Long) value) > Long.valueOf(operatorNum);
        Boolean isInterGreaterThan = (value instanceof Integer) && ((Integer) value) > Integer.valueOf(operatorNum);
        Boolean isShotGreaterThan = (value instanceof Short) && ((Short) value) > Short.valueOf(operatorNum);
        Boolean isFloatGreaterThan = (value instanceof Float) && ((Float) value) > Float.valueOf(operatorNum);
        Boolean isDoubleGreaterThan = (value instanceof Double) && ((Double) value) > Double.valueOf(operatorNum);
        Boolean isBigDecimalGreaterThan = (value instanceof BigDecimal) &&
                ((BigDecimal) value).compareTo(new BigDecimal(operatorNum)) > 0;
        Boolean isCollectionGreaterThan = (value instanceof Collection) && ((Collection) value).size() > Integer.valueOf(operatorNum);
        if (isStringGreaterThan || isLongGreaterThan || isInterGreaterThan || isShotGreaterThan || isFloatGreaterThan
            || isDoubleGreaterThan || isBigDecimalGreaterThan || isCollectionGreaterThan) {
            isGreaterThan = Boolean.TRUE;
        }
        return isGreaterThan;
    }

    /**
     * 是否大于等于
     *
     * @param value 字段值
     * @param operatorNum 操作数
     * @return 是否大于等于
     */
    private static Boolean isGreaterThanEqual(Object value, String operatorNum) {
        Boolean isGreaterThanEqual = Boolean.FALSE;
        if (value == null) {
            isGreaterThanEqual = Boolean.FALSE;
        }
        Boolean isStringGreaterThanEqual = (value instanceof String) && ((String) value).length() >= Integer.valueOf(operatorNum);
        Boolean isLongGreaterThanEqual = (value instanceof Long) && ((Long) value) >= Long.valueOf(operatorNum);
        Boolean isInterGreaterThanEqual = (value instanceof Integer) && ((Integer) value) >= Integer.valueOf(operatorNum);
        Boolean isShotGreaterThanEqual = (value instanceof Short) && ((Short) value) >= Short.valueOf(operatorNum);
        Boolean isFloatGreaterThanEqual = (value instanceof Float) && ((Float) value) >= Float.valueOf(operatorNum);
        Boolean isDoubleGreaterThanEqual = (value instanceof Double) && ((Double) value) >= Double.valueOf(operatorNum);
        Boolean isBigDecimalGreaterThanEqual = (value instanceof BigDecimal) &&
                ((BigDecimal) value).compareTo(new BigDecimal(operatorNum)) >= 0;
        Boolean isCollectionGreaterThanEqual = (value instanceof Collection) && ((Collection) value).size() >= Integer.valueOf(operatorNum);
        if (isStringGreaterThanEqual || isLongGreaterThanEqual || isInterGreaterThanEqual || isShotGreaterThanEqual || isFloatGreaterThanEqual
                || isDoubleGreaterThanEqual || isBigDecimalGreaterThanEqual || isCollectionGreaterThanEqual) {
            isGreaterThanEqual = Boolean.TRUE;
        }
        return isGreaterThanEqual;
    }

    /**
     * 是否小于
     *
     * @param value 字段值
     * @param operatorNum 操作数
     * @return 是否小于
     */
    private static Boolean isLessThan(Object value, String operatorNum) {
        Boolean isLessThan = Boolean.FALSE;
        if (value == null) {
            isLessThan = Boolean.FALSE;
        }
        Boolean isStringLessThan = (value instanceof String) && ((String) value).length() < Integer.valueOf(operatorNum);
        Boolean isLongLessThan = (value instanceof Long) && ((Long) value) < Long.valueOf(operatorNum);
        Boolean isInterLessThan = (value instanceof Integer) && ((Integer) value) < Integer.valueOf(operatorNum);
        Boolean isShotLessThan = (value instanceof Short) && ((Short) value) < Short.valueOf(operatorNum);
        Boolean isFloatLessThan = (value instanceof Float) && ((Float) value) < Float.valueOf(operatorNum);
        Boolean isDoubleLessThan = (value instanceof Double) && ((Double) value) < Double.valueOf(operatorNum);
        Boolean isBigDecimalLessThan = (value instanceof BigDecimal) &&
                ((BigDecimal) value).compareTo(new BigDecimal(operatorNum)) < 0;
        Boolean isCollectionLessThan = (value instanceof Collection) && ((Collection) value).size() < Integer.valueOf(operatorNum);
        if (isStringLessThan || isLongLessThan || isInterLessThan || isShotLessThan || isFloatLessThan
                || isDoubleLessThan || isBigDecimalLessThan || isCollectionLessThan) {
            isLessThan = Boolean.TRUE;
        }
        return isLessThan;
    }

    /**
     * 是否小于等于
     *
     * @param value 字段值
     * @param operatorNum 操作数
     * @return 是否小于等于
     */
    private static Boolean isLessThanEqual(Object value, String operatorNum) {
        Boolean isLessThanEqual = Boolean.FALSE;
        if (value == null) {
            isLessThanEqual = Boolean.FALSE;
        }
        Boolean isStringLessThanEqual = (value instanceof String) && ((String) value).length() <= Integer.valueOf(operatorNum);
        Boolean isLongLessThanEqual = (value instanceof Long) && ((Long) value) <= Long.valueOf(operatorNum);
        Boolean isInterLessThanEqual = (value instanceof Integer) && ((Integer) value) <= Integer.valueOf(operatorNum);
        Boolean isShotLessThanEqual = (value instanceof Short) && ((Short) value) <= Short.valueOf(operatorNum);
        Boolean isFloatLessThanEqual = (value instanceof Float) && ((Float) value) <= Float.valueOf(operatorNum);
        Boolean isDoubleLessThanEqual = (value instanceof Double) && ((Double) value) <= Double.valueOf(operatorNum);
        Boolean isBigDecimalLessThanEqual = (value instanceof BigDecimal) &&
                ((BigDecimal) value).compareTo(new BigDecimal(operatorNum)) <= 0;
        Boolean isCollectionLessThanEqual = (value instanceof Collection) && ((Collection) value).size() <= Integer.valueOf(operatorNum);
        if (isStringLessThanEqual || isLongLessThanEqual || isInterLessThanEqual || isShotLessThanEqual || isFloatLessThanEqual
                || isDoubleLessThanEqual || isBigDecimalLessThanEqual || isCollectionLessThanEqual) {
            isLessThanEqual = Boolean.TRUE;
        }
        return isLessThanEqual;
    }

    /**
     * 是否不等于
     *
     * @param value 字段值
     * @param operatorNum 操作数
     * @return 是否不等于
     */
    private static Boolean isNotEqual(Object value, String operatorNum) {
        Boolean isNotEqual = Boolean.FALSE;
        if (value == null) {
            isNotEqual = Boolean.FALSE;
        }
        Boolean isStringNotEqual = (value instanceof String) && !StringUtils.equals((String)value, operatorNum);
        Boolean isLongNotEqual = (value instanceof Long) && !value.equals(Long.valueOf(operatorNum));
        Boolean isInterNotEqual = (value instanceof Integer) && !value.equals(Integer.valueOf(operatorNum));
        Boolean isShotNotEqual = (value instanceof Short) && !value.equals(Short.valueOf(operatorNum));
        Boolean isFloatNotEqual = (value instanceof Float) && !value.equals(Float.valueOf(operatorNum));
        Boolean isDoubleNotEqual = (value instanceof Double) && !value.equals(Double.valueOf(operatorNum));
        Boolean isBigDecimalNotEqual = (value instanceof BigDecimal) &&
                ((BigDecimal) value).compareTo(new BigDecimal(operatorNum)) != 0;
        Boolean isCollectionNotEqual = (value instanceof Collection) && ((Collection) value).size() <= Integer.valueOf(operatorNum);
        if (isStringNotEqual || isLongNotEqual || isInterNotEqual || isShotNotEqual || isFloatNotEqual
                || isDoubleNotEqual || isBigDecimalNotEqual || isCollectionNotEqual) {
            isNotEqual = Boolean.TRUE;
        }
        return isNotEqual;
    }
    //-------- 对不同类型的值进行校验 -------

    /**
     * 判断是否符合参数规则
     *
     * @param method 方法
     * @param args 方法参数
     * @return 是否符合
     */
    private Boolean isCheck(Method method, Object[] args) {
        Boolean isCheck = Boolean.TRUE;
        //只允许有一个参数
        if (!method.isAnnotationPresent(Check.class)
                || args == null
                || args.length != 1) {
            isCheck = Boolean.FALSE;
        }
        return isCheck;
    }

    /**
     * 获取方法
     * @param point ProceedingJoinPoint
     * @return 方法
     */
    private Method getMethod(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = point.getTarget().getClass()
                        .getDeclaredMethod(point.getSignature().getName(), method.getParameterTypes());
            } catch(SecurityException | NoSuchMethodException e) {
                log.error("get method faild!", e);
            }
        }
        return method;
    }

    /**
     * 字段信息
     */
    class FieldInfo {
        /**
         * 字段
         */
        String field;
        /**
         * 提示信息
         */
        String innerMsg;
        /**
         * 操作符
         */
        String operator;
        /**
         * 操作数
         */
        String operatorNum;
        /**
         * 操作枚举
         */
        Operator optEnum;
    }

    /**
     * 操作枚举，封装操作符和对应的校验规则
     */
    enum Operator {
        /**
         * 大于
         */
        GREATER_THAN(">", CheckParamAspect::isGreaterThan),
        /**
         * 大于等于
         */
        GREATER_THAN_EQUAL(">=", CheckParamAspect::isGreaterThanEqual),
        /**
         * 小于
         */
        LESS_THAN("<", CheckParamAspect::isLessThan),
        /**
         * 小于等于
         */
        LESS_THAN_EQUAL("<=", CheckParamAspect::isLessThanEqual),
        /**
         * 不等于
         */
        NOT_EQUAL("!=", CheckParamAspect::isNotEqual),
        /**
         * 不为空
         */
        NOT_NULL("not null", CheckParamAspect::isNotNull);

        private String value;

        /**
         * BiFunction：接收字段值(Object)和操作数(String)，返回是否符合规则(Boolean)
         */
        private BiFunction<Object, String, Boolean> fun;

        Operator(String value, BiFunction<Object, String, Boolean> fun) {
            this.value = value;
            this.fun = fun;
        }
    }

    private static final String SEPARATOR = ":";
}
