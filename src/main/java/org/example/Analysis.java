package org.example;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.example.CCNVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Some code that uses JavaSymbolSolver.
 */
public class Analysis {
    private static List<Class<? extends Statement>> BRANCH_STATEMENTS = List.of(
            ForEachStmt.class,
            ContinueStmt.class,
            WhileStmt.class,
            IfStmt.class,
            BreakStmt.class,
            ForStmt.class,
            DoStmt.class
    );

    private static List<Class<? extends Statement>> EXIT_POINT_STATEMENTS = List.of(ReturnStmt.class);

    public static Map<String, Integer> calculateCCN(Path path) {
        try {
            return calculateCCN(Files.readString(path));
        } catch (IOException e) {
            System.err.printf("Path '%s' could not be read!\n", path);
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Integer> calculateCCN(String javaCode) {
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());

        // Configure JavaParser to use type resolution
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        // Parse some code
        CompilationUnit cu = StaticJavaParser.parse(javaCode);

        Map<String, Integer> methodToCCN = new HashMap<>();

        List<MethodDeclaration> methodDeclarations = cu.findAll(MethodDeclaration.class);
        for (MethodDeclaration md : methodDeclarations) {
            if(md.getBody().isEmpty()) {
                continue;
            }
            CCNVisitor visitor = new CCNVisitor();
            BlockStmt body = md.getBody().get();

            body.accept(visitor, null);
            
            methodToCCN.put(md.getDeclarationAsString(), visitor.getResult());
        }

        return methodToCCN;
    }

    public static void main(String[] args) {
        for (String arg : args) {
            Path path = Path.of(arg);

            if (Files.notExists(path)) {
                System.out.printf("File '%s' does not exist! This argument is skipped.\n", path);
                continue;
            }

            System.out.println(arg);
            Map<String, Integer> ccnMap = calculateCCN(path);
            ccnMap.forEach((key, value) -> System.out.printf("\t%s - %d\n", key, value));
        }
    }
}
