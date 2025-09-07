import java.util.ArrayList;

public class Main {

    static public void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8,      2, 3, 4, 2, 2, 2,      2, 2, 3, 4, 5, 6, 6, 8, 9,     7, 6, 6, 6, 6, 6, 6, 8, 4, 6, 6, 6, 6, 6, 6, 6, 6,        3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 8   };
        int min_run = 6;

        ArrayList<Integer> runs = new ArrayList<>();

        InsertionSort auxInsertion = new InsertionSort();

        boolean isAscendent = true;
        boolean orderIsSet = false;
        boolean isBroken = false;
        boolean itwasreversed=false;

        int count = 0;
        int end=-1;
        boolean brokenAfter =true;
        int lastend;
        
        for (int i = 1; i <numbers.length; i++) {
          

            if(isBroken && count<min_run){

                //revisa que no sea ascendente y que no haya sido reversado ya
                if(!isAscendent && !itwasreversed){
                    reverse(numbers, end+1, i);
                    itwasreversed = true;
                }

                count++;
                //finalmente completar
                continue;
            }

            //setea el orden si es ascendente o descendente
            if(!orderIsSet){
                if (numbers[i] != numbers[i-1]) {
                    isAscendent = numbers[i] >= numbers[i-1];
                    orderIsSet=true;
                }else{
                    count++;
                    continue;
                }
            }

            if(isAscendent){
                if(numbers[i]<numbers[i-1]){
                    isBroken = true;
                    //si se quiebra en el momento que es menor al min run, se va a setar brokenafter como false, indicando que este fue quebrado antes de llegar al min run, en caso contrario true, que indica que se quebro al moento de llegar al run o despues
                    brokenAfter = (count >= min_run);
                }
                
            }else{
                if(numbers[i]>numbers[i-1]){
                    isBroken = true;
                    brokenAfter = (count > min_run);
                }
            }

            //completar

        


            if((isBroken && count>=min_run) || i==(numbers.length-1)){
                lastend = end;
                end= (numbers.length-1 == i) ? i :i-1;

                //cuando no sea ascendente ni haya sido reverseado
                if(!isAscendent  && !itwasreversed){
                    //invertir arreglo actual ss
                     reverse(numbers, lastend+1, end);
                    if(brokenAfter){
                                        System.out.printf("descendente y aplciar reverser [%d..%d]%n", lastend+1 , end);
                    }else{
 auxInsertion.generateInsertion(numbers, lastend+1, end);
                                        System.out.printf("descendente y aplciar ANTES E INSERTION SORT [%d..%d]%n", lastend+1 , end);

                    }

                }
                
                //cunado no seas ascendente y haya sido reverseado
                 if(!isAscendent  && itwasreversed){
                  
                    //insertion sort
                     auxInsertion.generateInsertion(numbers, lastend+1, end);
                    System.out.printf("descendente y ya se aplico reverse, necesitamos insertion sort [%d..%d]%n", lastend+1 , end);
                }
                
                //cando es ascendente y el contador de brokens es mayor a 1 se usa insertion sort
                if(isAscendent && !brokenAfter){
                    //inserion sort
                     auxInsertion.generateInsertion(numbers, lastend+1, end);
                                        System.out.printf("ascendente y quebro antes del 6 InsertionSort [%d..%d ]%n", lastend+1 , end);

                }

                     if(isAscendent && brokenAfter){
                                                                System.out.printf("ascendente no se requiere nada [%d..%d ]%n", lastend+1 , end);

                     }

                //terminar 
                runs.add(end);
                isBroken = false;
                orderIsSet = false;
                itwasreversed=false;
                count = 0;
                
            }    
            
            count++;
        }

        // for(int i=0;i<runs.size();i++){
        //     System.out.println(runs.get(i));
        // }

        for(int i=0;i<numbers.length;i++){
            System.out.println(numbers[i]);
        }
    }
public static void reverse(int[] numbers, int idxStart, int idxEnd) {
    while (idxStart < idxEnd) {
        int temp = numbers[idxStart];
        numbers[idxStart] = numbers[idxEnd];
        numbers[idxEnd] = temp;
        idxStart++;
        idxEnd--;
    }
}
    
}
