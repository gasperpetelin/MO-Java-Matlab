classdef ScriptRunner
   properties
      NumberOfVariables = 3
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