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

import java.io.File;
import org.junit.Test;
import org.sonar.javascript.checks.verifier.JavaScriptCheckVerifier;

/**
 * Test class to test the check implementation.
 */
public class DataGridCellsOperateCheckTest {

  @Test
  public void test() throws Exception {
    JavaScriptCheckVerifier.issues(new DataGridCellsOperateCheck(), new File("src/test/resources/checks/dataGridCellsOperateCheck.js"))
      .next().atLine(1).withMessage("不允许直接使用数字下标获取表格列进行操作.")
      .next().atLine(2).withMessage("不允许直接使用数字下标获取表格列进行操作.")
      .next().atLine(3).withMessage("不允许直接使用数字下标获取表格列进行操作.")
      .noMore();
  }

}
