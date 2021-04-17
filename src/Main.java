import java.util.*;

public class Main {
    public static void main( String[] args ) {

        HashMap<String, Integer> signH = new HashMap<>();
        signH.put("(", 3);
        signH.put(")", 4);
        signH.put("=", 5);
        signH.put("*", 2);
        signH.put("/", 2);
        signH.put("+", 1);
        signH.put("-", 1);

        Queue<String> queue = new LinkedList();
        queue.add("1");
        queue.add("+");
        queue.add("2");
        queue.add("*");
        queue.add("(");
        queue.add("4");
        queue.add("-");
        queue.add("1");
        queue.add(")");
        queue.add("=");

        System.out.println( "Result is " + doAlgoritm(queue, signH) );
    }

    public static Double doAlgoritm(Queue<String>  queue, HashMap<String, Integer> signH) {
        Stack<Double> stackNumb = new Stack<>();
        Stack<String> stackSigne = new Stack<>();

        Integer priority = null;
        //boolean flag = true;

        //while(flag){
        while (true){
            String value = queue.poll();
            priority = signH.get(value);
            if( priority != null ){
                /* the sign stack is empty, just put first sign to it */
                if(stackSigne.size() == 0){
                    stackSigne.push(value);
                }
                /* we start calculating of value in brackets separately*/
                else if(priority == signH.get("(") ){
                    stackNumb.push(doAlgoritm(queue, signH));
                }
                /* the end of expression, the result should be provided */
                else if(priority == signH.get(")") || priority == signH.get("=")){
                    calculate(stackNumb, stackSigne);
                    return stackNumb.pop();
                }
                /* the new operator have higher priority, put it to stack */
                else if(priority > signH.get(stackSigne.peek())){
                    stackSigne.push(value);
                }
                /* the previous operator has higher priority and can be executed as other operators with same priority*/
                else {
                    while (priority <= signH.get(stackSigne.peek()) && stackNumb.size()>1){
                        Double secondValue = stackNumb.pop();
                        Double firstValue = stackNumb.pop();
                        String signe = stackSigne.pop();
                        stackNumb.push( execute( firstValue, secondValue, signe ));
                    }
                    stackSigne.push(value);
                }
            }
            else {
                stackNumb.push(Double.parseDouble(value));
            }
        }

        //return null;
    }

    private static void calculate(Stack<Double> numStack, Stack<String> signStack){
        while (numStack.size()>1){
            Double secondValue = numStack.pop();
            Double firstValue = numStack.pop();
            String sign = signStack.pop();
            numStack.push( execute( firstValue, secondValue, sign ));
        }
    }
    private static Double execute (Double firstNum, Double secondNum, String operSign){
        if (operSign.equals("+")){
            return (firstNum + secondNum);
        }
        else if(operSign.equals("-")){
            return (firstNum - secondNum);
        }
        else if(operSign.equals("*")){
            return (firstNum * secondNum);
        }
        else if(operSign.equals("/")){
            return (firstNum / secondNum);
        }
        return null;
    }
}