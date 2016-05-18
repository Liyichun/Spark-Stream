package antlr;// Generated from /Users/cynric/workspaces/antlr/pds/pds/src/Pds.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PdsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PdsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PdsParser#pds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPds(PdsParser.PdsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PdsParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(PdsParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link PdsParser#rules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRules(PdsParser.RulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PdsParser#trans_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrans_rule(PdsParser.Trans_ruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link PdsParser#conf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConf(PdsParser.ConfContext ctx);
	/**
	 * Visit a parse tree produced by {@link PdsParser#stack}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStack(PdsParser.StackContext ctx);
	/**
	 * Visit a parse tree produced by {@link PdsParser#stack_content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStack_content(PdsParser.Stack_contentContext ctx);
}