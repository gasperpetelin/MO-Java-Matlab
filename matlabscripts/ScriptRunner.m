classdef ScriptRunner
   properties
      N1 = [];
      N2 = [];
      R_d = 13190;
      %NumberOfVariables = 3
      %NumberOfObjectives = 10
      %Name = 'Zagon'
      %Limits=[[-100,100];[-100,100];[-100,100];[-100,100];[-100,100];[-100,100]];
   end
   methods

      function obj = ScriptRunner()
          f = load('C:\Users\Gasper\Desktop\DME.mat')
          N1 = f.B{1};
          N2 = f.B{2};
      end
      
      function o = evaluate(obj, solution)
        %13190*30 + 30*30*2
        n= 13190;
        k = 5;
        G = reshape(solution(1:(n*k)), [n,k]);
        f = n*k + 1;
        t = f+k*k -1;
        S1 = reshape(solution(f:t), [k, k]);
        S2 = reshape(solution(t+1:end), [k, k]);
        
        %S1M = rand(size(S1))<0.05;
        %S1=S1.*S1M;
        
        %S2M = rand(size(S2))<0.05;
        %S2=S2.*S2M
        
          f = load('C:\Users\Gasper\Desktop\DME.mat');
          N1 = f.B{1};
          N2 = f.B{2};
        
        d = (norm(N1,'fro'))^2 + (norm(N2,'fro'))^2;
        i = (norm(N1 - G*S1*G', 'fro' ))^2 + (norm(N2 - G*S2*G', 'fro' ))^2;
        
        
        q = (i/d)
        %v = (abs(N2 - G*S1*G') + abs(N1 - G*S2*G'));
        %o = v(:);
        
        
        o(1) = (i/d)

      end
   end
end