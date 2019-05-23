package club.jackzhan.cloudstore.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * Description: 全局事务处理
 * 通过AOP切面设置全局事务，拦截service包下面所有方法
 * AOP术语：通知（Advice）、连接点（Joinpoint）、切入点（Pointcut)、切面（Aspect）、目标(Target)、代理(Proxy)、织入（Weaving）
 * Date: 2019-05-23 10:50
 *
 * @Author: JackZhan
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {
    /**
     * 定义切点变量：拦截xx.xxx.xxxxx.****.***.service包下所有类的所有方法,返回值类型任意的方法
     */
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* club.jackzhan.cloudstore.service.impl.*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {

        /*事务管理规则，声明具备事务管理的方法名*/
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*", requiredTransactionRule());
        source.addTransactionalMethod("save*", requiredTransactionRule());
        source.addTransactionalMethod("insert*", requiredTransactionRule());
        source.addTransactionalMethod("delete*", requiredTransactionRule());
        source.addTransactionalMethod("update*", requiredTransactionRule());
        source.addTransactionalMethod("create*", requiredTransactionRule());
        source.addTransactionalMethod("exec*", requiredTransactionRule());
        source.addTransactionalMethod("set*", requiredTransactionRule());
        source.addTransactionalMethod("get*", readOnlyTransactionRule());
        source.addTransactionalMethod("query*", readOnlyTransactionRule());
        source.addTransactionalMethod("find*", readOnlyTransactionRule());
        source.addTransactionalMethod("list*", readOnlyTransactionRule());
        source.addTransactionalMethod("count*", readOnlyTransactionRule());
        source.addTransactionalMethod("is*", readOnlyTransactionRule());
        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 利用AspectJExpressionPointcut设置切面=切点+通知（写成内部bean的方式）
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        /* 声明切点的面
         * 切面（Aspect）：切面就是通知和切入点的结合。通知和切入点共同定义了关于切面的全部内容——它的功能、在何时和何地完成其功能。
         * */
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        /*声明和设置需要拦截的方法,用切点语言描写*/
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        /*设置切面=切点pointcut+通知TxAdvice*/
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }


    /**
     * 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 {@link org.springframework.transaction.annotation.Propagation#REQUIRED}
     */
    private RuleBasedTransactionAttribute requiredTransactionRule() {
        RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();

        /*添加对所有EXCEPTON异常进行事务回滚*/
        required.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        /*PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。 */
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*设置事务失效时间，如果超过5秒，则回滚事务*/
        required.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);

        return required;
    }

    /**
     * 只读事务 {@link org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED}
     */
    private RuleBasedTransactionAttribute readOnlyTransactionRule() {
        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        /*设置当前事务是否为只读事务，true为只读*/
        readOnly.setReadOnly(true);
        /* transactiondefinition 定义事务的隔离级别；
         * PROPAGATION_NOT_SUPPORTED事务传播级别5，以非事务运行，如果当前存在事务，则把当前事务挂起*/
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        return readOnly;
    }

}
