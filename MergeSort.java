import java.util.Arrays;

public class MergeSort{
 
 
    public MergeSort(){

    }

    //hacer merge de un numero, ingresando un array de indexes de sus runs
    public int[] mergeRuns(int[] numbers, int[] runs) {
        
        int lastEnd = 0;

        int [][] subRuns = new int[runs.length][];
     
        //formando el array de subarrys, es una array que contiene los sub arrays de los runs
        for (int i = 0; i < runs.length; i++) {
            int end = runs[i]+1; //este indice se necesita agregar + 1 porque no se toma en cuenta, por ejemplo si el indice final es 6, esl 6 no se toma en cuenta, tiene que ser 7
            subRuns[i] = Arrays.copyOfRange(numbers, lastEnd, end); //creando el sub array
            lastEnd=end;
        }

        //mientras aun existe mas de 1 subrun en el arreglo, se seguiran mezclando en pares
        while(subRuns.length>1){

            //creando el nuevo tamano del arreglo, cada vez va a tender a reducirse casi en la mitad, por ejemplo si mide 6 se va reducir a 3, si mide 3 a 2, porque va hacer merge en 2 arrays y el otro no tiene pareja, por eso el resultado seria 3, ese +1 evita que se redonde hacia abajo demas
            int newSize = (subRuns.length+1)/2;
            int merged[][] = new int[newSize][]; //se depositaran los subarrays ya mezclados en 2 en 2
            int counterMerged = 0; //aqui aumentaremos cuando agregemos elementos al merged[], puede ser porque se mezclaron 2 subarrays o porque el ultimo no tiene par para mezclarse
            
            for (int i = 0; i < subRuns.length; i+=2) {
                if((i+1)<subRuns.length){ //si aun hay oportunidad para mezclar una pareja de subarrays, se hace
                    merged[counterMerged++]=merge(subRuns[i], subRuns[i+1]);
                }else{ //en caso de que no haya oportunidad, eso quiere decir que solamente existe 1 sub array, no tiene pareja, por lo cual se va regresar a merged, esperando que en la proxima iteracion pueda mezclarse
                    merged[counterMerged++]=subRuns[i];
                }
            }

            //actualizamos los sub runs
            subRuns = Arrays.copyOf(merged, counterMerged);

        }

        return  subRuns[0];
    }


    //hace merge de 2 arrays (merge sort)
    public int[] merge(int[] arr1, int[] arr2) {

        int[] newArray = new int[arr1.length + arr2.length];
        int count1 = 0;
        int count2 = 0;

        do{

            if(count1 == arr1.length){
                newArray[count1 + count2] = arr2[count2];
                count2++;
                continue;
            }

            if(count2 == arr2.length){
                newArray[count1 + count2] = arr1[count1];
                count1++;
                continue;
            }

            if (arr1[count1] <= arr2[count2]) {
                newArray[count1 + count2] = arr1[count1];
                count1++;
            } else {
                newArray[count1 + count2] = arr2[count2];
                count2++;
            }

        }while((count1+count2) < newArray.length);

        return newArray;
    }

}
