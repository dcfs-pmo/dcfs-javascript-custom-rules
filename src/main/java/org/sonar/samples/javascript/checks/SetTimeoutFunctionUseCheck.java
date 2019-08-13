/*
 * SonarQube JavaScript Custom Rules Example
 * Copyright (C) 2016-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.samples.javascript.checks;

import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.expression.ArgumentListTree;
import org.sonar.plugins.javascript.api.tree.expression.CallExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.ExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.IdentifierTree;
import org.sonar.plugins.javascript.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.javascript.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.google.common.collect.ImmutableSet;

/**
 * Example of a check extending {@link SubscriptionVisitorCheck}.
 * 
 * We define the kinds of the nodes that we subscribe to in
 * {@link #nodesToVisit()}. We can then override visitNode or leaveNode: these
 * methods will be called for all nodes of the kinds we subscribed to.
 */
@Rule(key = "setTimeout", priority = Priority.MAJOR, name = "定时器函数的执行参数使用方法名字符串，其实会使用eval()执行函数", tags = {
		"convention","html5" }, description = "定时器函数的执行参数使用方法名字符串，其实会使用eval()执行函数")
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.DATA_RELIABILITY)
@SqaleConstantRemediation("2min")
public class SetTimeoutFunctionUseCheck extends DoubleDispatchVisitorCheck  {

	private static final Set<String> FORBIDDEN_FUNCTIONS = ImmutableSet.of("setTimeout","setInterval");

	@Override
	public void visitCallExpression(CallExpressionTree tree) { 
		if(tree.callee().is(Kind.IDENTIFIER_REFERENCE)){
		IdentifierTree callee = (IdentifierTree) tree.callee();
	    if (callee.is(Kind.IDENTIFIER_REFERENCE) && FORBIDDEN_FUNCTIONS.contains((callee.identifierToken().text()))) {
	        ArgumentListTree args = tree.argumentClause();
	        ExpressionTree value = args.arguments().get(0);
	    	if(value.is(Kind.STRING_LITERAL) ){
	    	      addIssue(tree, "定时器函数的执行参数使用方法名字符串，其实会使用eval()执行函数");
	    	}
	    }
		}

		super.visitCallExpression(tree);
	}

	 
}
