package interpreter.view;

import java.util.Scanner;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;

public class View {
    private void printMenu() {
        System.out.println("1. Run Example 1");
        System.out.println("2. Run Example 2");
        System.out.println("3. Run Example 3");
        System.out.println("0. Exit");
    }

   public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String option = scanner.nextLine();
            try{
                switch (option) {
                    case "1":
                        System.out.println("Running Example 1...");
                        runProgram(createExample1());
                        break;
                    case "2":
                        System.out.println("Running Example 2...");
                        runProgram(createExample2());
                        break;
                    case "3":
                        System.out.println("Running Example 3...");
                        runProgram(createExample3());
                        break;
                    case "0":
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }


        }
        scanner.close();
    }


    private void runProgram(IStmt program) {
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, interpreter.model.value.IValue> symTable = new MyDictionary<>();
        MyList<interpreter.model.value.IValue> out = new MyList<>();

        PrgState state = new PrgState(exeStack, symTable, out, program);
        IRepository repository = new interpreter.repository.Repository(state);
        Controller controller = new Controller(repository);

        controller.allStep();
    }

    private IStmt createExample1() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
    }

    private IStmt createExample2() {
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a",
                                new ArithExp(ArithOp.ADD, new ValExp(new IntValue(2)),
                                        new ArithExp(ArithOp.MUL, new ValExp(new IntValue(3)),
                                                new ValExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",
                                        new ArithExp(ArithOp.ADD, new VarExp("a"),
                                                new ValExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

    }

    private IStmt createExample3() {
        return new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValExp(new IntValue(2))),
                                        new AssignStmt("v", new ValExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
    }
}




