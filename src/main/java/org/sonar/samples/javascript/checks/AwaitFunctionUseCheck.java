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

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.javascript.tree.KindSet;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.declaration.FunctionDeclarationTree;
import org.sonar.plugins.javascript.api.tree.declaration.FunctionTree;
import org.sonar.plugins.javascript.api.tree.expression.CallExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.ExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.IdentifierTree;
import org.sonar.plugins.javascript.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * Example of a check extending {@link SubscriptionVisitorCheck}.
 * 
 * We define the kinds of the nodes that we subscribe to in
 * {@link #nodesToVisit()}. We can then override visitNode or leaveNode: these
 * methods will be called for all nodes of the kinds we subscribed to.
 */
@Rule(key = "await-async", priority = Priority.MAJOR, name = "如果表达式出  await 关键字，则父方法必须使用async修饰 ，否则运行时抛出异常", tags = { "convention","es2015" }, description = "如果表达式出  await 关键字，则父方法必须使用async修饰 ，否则运行时抛出异常")
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.DATA_RELIABILITY)
@SqaleConstantRemediation("5min")
public class AwaitFunctionUseCheck extends SubscriptionVisitorCheck {

	@Override
	public Set<Kind> nodesToVisit() {
		return ImmutableSet.of(Kind.AWAIT);
	}

	@Override
	public void visitNode(Tree tree) {
		// we can do this cast because we subscribed only to nodes of kind
		// CALL_EXPRESSION
		if (tree.is(Kind.AWAIT)) {
			FunctionTree parentTree = this.getFuntionDeclaration(tree);
			if (null != parentTree && null != parentTree.asyncToken()) {
				// System.out.println("OK");
			} else {
				addIssue(parentTree, "如果表达式出   await 关键字，则父方法必须使用async修饰 ，否则运行时抛出异常");
				// System.err.println("Fail");
			}
		}

		super.visitNode(tree);
	}

	private FunctionTree getFuntionDeclaration(Tree tree) {
		if (tree.is(Kind.FUNCTION_DECLARATION)
				|| tree.is(Kind.FUNCTION_EXPRESSION)) {
			return (FunctionTree) tree;
		}

		if (null == tree.parent()) {
			return null;
		}
		return getFuntionDeclaration(tree.parent());

	}
}
