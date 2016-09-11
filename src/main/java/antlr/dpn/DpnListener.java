// Generated from /Users/cynric/workspaces/antlr/pds/pds/src/Dpn.g4 by ANTLR 4.5.3
package antlr.dpn;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DpnParser}.
 */
public interface DpnListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DpnParser#dpn}.
	 * @param ctx the parse tree
	 */
	void enterDpn(DpnParser.DpnContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#dpn}.
	 * @param ctx the parse tree
	 */
	void exitDpn(DpnParser.DpnContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#transList}.
	 * @param ctx the parse tree
	 */
	void enterTransList(DpnParser.TransListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#transList}.
	 * @param ctx the parse tree
	 */
	void exitTransList(DpnParser.TransListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#ruleList}.
	 * @param ctx the parse tree
	 */
	void enterRuleList(DpnParser.RuleListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#ruleList}.
	 * @param ctx the parse tree
	 */
	void exitRuleList(DpnParser.RuleListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#trans}.
	 * @param ctx the parse tree
	 */
	void enterTrans(DpnParser.TransContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#trans}.
	 * @param ctx the parse tree
	 */
	void exitTrans(DpnParser.TransContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#trans_rule}.
	 * @param ctx the parse tree
	 */
	void enterTrans_rule(DpnParser.Trans_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#trans_rule}.
	 * @param ctx the parse tree
	 */
	void exitTrans_rule(DpnParser.Trans_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#multi}.
	 * @param ctx the parse tree
	 */
	void enterMulti(DpnParser.MultiContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#multi}.
	 * @param ctx the parse tree
	 */
	void exitMulti(DpnParser.MultiContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#conf}.
	 * @param ctx the parse tree
	 */
	void enterConf(DpnParser.ConfContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#conf}.
	 * @param ctx the parse tree
	 */
	void exitConf(DpnParser.ConfContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#stack}.
	 * @param ctx the parse tree
	 */
	void enterStack(DpnParser.StackContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#stack}.
	 * @param ctx the parse tree
	 */
	void exitStack(DpnParser.StackContext ctx);
	/**
	 * Enter a parse tree produced by {@link DpnParser#stack_content}.
	 * @param ctx the parse tree
	 */
	void enterStack_content(DpnParser.Stack_contentContext ctx);
	/**
	 * Exit a parse tree produced by {@link DpnParser#stack_content}.
	 * @param ctx the parse tree
	 */
	void exitStack_content(DpnParser.Stack_contentContext ctx);
}