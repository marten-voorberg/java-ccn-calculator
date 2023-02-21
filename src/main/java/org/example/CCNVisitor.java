package org.example;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

public class CCNVisitor extends VoidVisitorWithDefaults<Object> {
    private int result;

    public CCNVisitor() {
        result = 2;
    }

    @Override
    public void visit(IfStmt n, Object o) {
        result++;
        super.visit(n, o);
    }

    @Override
    public void visit(ForEachStmt n, Object o) {
        result++;
        super.visit(n, o);
    }

    @Override
    public void visit(ContinueStmt n, Object o) {
        result++;
        super.visit(n, o);
    }

    @Override
    public void visit(WhileStmt n, Object o) {
        result++;
        super.visit(n, o);
    }

    @Override
    public void visit(BreakStmt n, Object o) {
        result++;
        super.visit(n, o);
    }

    @Override
    public void visit(ForStmt n, Object o) {
        result++;
        super.visit(n, o);
    }

    @Override
    public void visit(DoStmt n, Object o) {
        result++;
        super.visit(n, o);
    }

    @Override
    public void visit(ReturnStmt n, Object arg) {
        result--;
        super.visit(n, arg);
    }

    public void defaultAction(Node node, Object o) {
        node.getChildNodes().forEach(child -> child.accept(this, o));
    }

    public void defaultAction(NodeList nodeList, Object o) {
    }

    public int getResult() {
        return result;
    }
}
