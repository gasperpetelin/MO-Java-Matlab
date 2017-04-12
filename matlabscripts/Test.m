classdef Test
   properties
      NumberOfVariables = 2
      NumberOfObjectives = 2
      Name = 'Test problem'
      Limits = [[-7, 4];[-7, 4];];

   end
   methods
      
      function obj = Test()
      end
      
      function o = evaluate(obj, solution)
        o(1) = solution(1)^2 - solution(2);
        o(2) = -0.5*solution(1)-solution(2)-1;
      end

   end
end