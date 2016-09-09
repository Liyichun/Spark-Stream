package antlr.simple;// Generated from /Users/cynric/workspaces/antlr/pds/pds/src/Pds.g4 by ANTLR 4.5.1

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import model.pds.simple.Configuration;
import model.pds.simple.TransRule;


/**
 * This class provides an empty implementation of {@link PdsVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public class PdsBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements PdsVisitor<T> {
    public Container container;

    public PdsBaseVisitor(Container container) {
        this.container = container;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitPds(PdsParser.PdsContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitStart(PdsParser.StartContext ctx) {
        // add new
        container.startConf = Configuration.fromAntlrContext((PdsParser.ConfContext) ctx.getChild(1));
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitRules(PdsParser.RulesContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitTrans_rule(PdsParser.Trans_ruleContext ctx) {
        // add new
        container.ruleSet.add(TransRule.fromTransContext(ctx));
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitConf(PdsParser.ConfContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitStack(PdsParser.StackContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitStack_content(PdsParser.Stack_contentContext ctx) {
        return visitChildren(ctx);
    }
}