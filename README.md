# jMetal problem evaluation in Matlab

### ZagonTest
Primer klica datoteke ZagonTest



Abstraktni razred MatlabManagerConfig predstavlja skupek podatkov, ki so 
potrebni za vsak problem. Razred lahko dodatno razširimo za točno določene probleme,
kot so DoubleSolutionMatlabManagerConfig in BinarySolutionMatlabManagerConfig, ki vsebujejo
še dodatne potrebne podatke.

MatlabManagerConfig:
* variableName = "variable123"
* evaluateMethod = "evaluate" 
* numberOfVariables = "NumberOfVariables" 
* numberOfObjectives = "NumberOfObjectives"

DoubleSolutionMatlabManagerConfig:
* variableLimits = "Limits"

BinarySolutionMatlabManagerConfig
* numberOfBitsPerVariable = "BitsPerVariable"

```java
/*Konfiguracijske datoteka iz katere preberemo imena polj v Matlab razredu.*/
DoubleSolutionMatlabManagerConfig conf = new DoubleSolutionMatlabManagerConfig();

```

MatlabManager je abstrakten razred, ki skrbi za vso komunikacijo med Java aplikacijo in Matlab programom.
Razred lahko razširimo za specifičen tip problema, kot je Double in Binary problem.

DoubleMatlabManager je namenjen reševanju problemov, ki imajo več double spremenljivk, zato razširja razred 
MatlabManager ter implementira ISolutionEvaluation, kar pomeni, da mu lahko pošljemo DoubleSolution,
in zna izračunati kakovost rešitve.

Pred prvim klicem je potrebno ustvariti še novo povezavo z Matlabom in nastaviti pot do .m datoteke

```java
/*Manager, ki komunicira z zunanjim programom in vrača ocene rešitev.*/
DoubleMatlabManager manager = new DoubleMatlabManager(conf);
manager.openSession();

manager.setPath("matlabscripts");

```

Za sestavo problema lahko uporabimo ProblemBuilder, ki mu podamo prej ustvarjenega managerja in ime 
datoteke v kateri se nahaja problem. ProblemBuilder s pomočjo managerja prebere potrebne podatke, ki
se uporabijo za sestavo problema (število spremenljivke, limite, ...). 

```java

ExternalDoubleProblem p = new DoubleProblemBuilder(manager, "ZagonTest").build();

Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<DoubleSolution>(p,
        new SBXCrossover(0.9, 20),
        new PolynomialMutation(2, 20))
        .build() ;
AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

System.out.println(algorithm.getResult());
System.out.println(algorithmRunner.getComputingTime());

manager.closeSession();
```

Datoteka ZagonTest

```matlab
classdef ZagonTest
   properties
      NumberOfVariables = 6
      NumberOfObjectives = 10
      Name = 'Zagon'
      Limits=[[-100,100];[-100,100];[-100,100];[-100,100];[-100,100];[-100,100]];
   end
   methods

      function obj = ZagonTest()
      end
      
      function o = evaluate(obj, solution)
        global vhod;
        vhod = solution;
        o = zagon(vhod);
      end
   end
end
```


```Matlab
classdef Knapsack
   properties
      NumberOfVariables = 3
      NumberOfObjectives = 2
      BitsPerVariable = 1
      Name = 'Knapsack problem'
      Elements
      Size
   end
   methods

      function obj = Knapsack(elements, size)
        obj.Elements = elements;
        obj.Size = size;
        obj.NumberOfVariables = length(elements);
      end
      
      function o = evaluate(obj, solution)
        %solution = solution(:, 1);
        o(1) = -sum(solution'.*obj.Elements);
        o(2) = sum(solution);
        if(o(1)<-obj.Size)
            o(1) = 0;
            o(2) = 0;
        end
      end
   end
end
```
