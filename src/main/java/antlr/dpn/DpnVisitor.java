// Generated from /Users/cynric/workspaces/antlr/pds/pds/src/Dpn.g4 by ANTLR 4.5.3
package antlr.dpn;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DpnParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DpnVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DpnParser#dpn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDpn(DpnParser.DpnContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#transList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransList(DpnParser.TransListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#ruleList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleList(DpnParser.RuleListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#trans}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrans(DpnParser.TransContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#trans_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrans_rule(DpnParser.Trans_ruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#multi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulti(DpnParser.MultiContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#conf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConf(DpnParser.ConfContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#stack}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStack(DpnParser.StackContext ctx);
	/**
	 * Visit a parse tree produced by {@link DpnParser#stack_content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStack_content(DpnParser.Stack_contentContext ctx);
}