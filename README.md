# jMetal problem evaluation in Matlab

### Poganjanje glavne skripte

Zastavice
* -v - število spremenljivk (obvezen)
* -o - število kriterijev (obvezen)
* -pop - velikost populacije
* -eval - število iteracij 
* -name - ime problema
* -p - pot do .m datoteke
* -minL - seznam spodnjih mej spremenljivk
* -maxL - seznam zgorjnjih mej spremenljivk
* -minLA - spodnja meja za vse spremenljivke
* -minLA - zgornja meja za vse spremenljivke
* -cross - tip križanja
* -mut - tip mutacije

Crossover
**Opcije**|**Primer**
___|___
-cross blxalpha probability [alpha] | -cross blxalpha 0.7 13
-cross null | -cross null
-coss |




### ScriptRunner

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

ExternalDoubleProblem p = new DoubleProblemBuilder(manager, "ScriptRunner").build();

```

V nadaljevanju isto kot pri navadnih problemih sestavimo algoritem in ga zaženemo.
Ob koncu izvajanja je potrebno zapreti povezavo z Matlabom, saj s tem prekinemo sejo.

```java

Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<DoubleSolution>(p,
        new SBXCrossover(0.9, 20),
        new PolynomialMutation(2, 20))
        .build() ;
AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

System.out.println(algorithm.getResult());
System.out.println(algorithmRunner.getComputingTime());

manager.closeSession();
```

Datoteka ScriptRunner

Skripta vsebuje razred, ki ima polja NumberOfVariables, NumberOfObjectives, Name in Limits, ki so
definirana v MatlabManagerConfig. Ta polja se uporabijo za sestavo problema. 

Razred vsebuje tudi funkcijo evaluate, ki vrne vektor dolžine NumberOfObjectives, ki vsebuje 
izračune posamezne rešitve.

```matlab
classdef ScriptRunner
   properties
      NumberOfVariables = 6
      NumberOfObjectives = 10
      Name = 'Zagon'
      Limits=[[-100,100];[-100,100];[-100,100];[-100,100];[-100,100];[-100,100]];
   end
   methods

      function obj = ScriptRunner()
      end
      
      function o = evaluate(obj, solution)
        global vhod;
        vhod = solution;
        o = zagon(vhod);
      end
   end
end
```

### Knapsack
Prikaz reševanja probleman Knapsack, kjer z razredom ProblemBuilder zgradimo potrebne
argumente za delovanje problema (velikost koša in elemente, ki jih zlagamo v koš) in 
jih pošljemo v konstruktor razreda

```java
BinarySolutionMatlabManagerConfig conf = new BinarySolutionMatlabManagerConfig();
BinaryMatlabManager manager = new BinaryMatlabManager(conf);
manager.openSession();
manager.setPath("C:\\matlabscripts");
BinaryProblemBuilder builder = new BinaryProblemBuilder(manager, "Knapsack")
        .startArray()
            .addValue(5)
            .addValue(3)
            .addValue(7)
            .addValue(2)
            .addValue(3)
            .addValue(3)
        .stopArray()
        .addValue(11);
ExternalBinaryProblem p = builder.build();
Algorithm<BinarySolution> algorithm1 = new NSGAII(p, 600, 20,
        new SinglePointCrossover(0.5),
        new BitFlipMutation(0.5),
        new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());
AlgorithmRunner algorithmRunner1 = new AlgorithmRunner.Executor(algorithm1).execute();
System.out.println(algorithm1.getResult());
System.out.println(p.getName());
manager.closeSession();
```

Matlab razred Knapsack, ki izračuna kvaliteto rešitve.

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
