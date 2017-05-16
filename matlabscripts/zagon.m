 function rezultat=zagon(vhod)

    x = vhod(1);
    y = vhod(2);
 
    rez(1) = x^2-y;% + (vhod(2)-3)^2;
    rez(2) = -.5*x-y-1;
    rez(3) = x^2-y;
    %rez(4) = y-2*x;
    
    %Viennet
    %rez(1) = 0.5*(x^2+y^2) + sin(x^2+y^2);
    %rez(2) = (3*x-2*y+4)^2/8 + (x-y+1)^2/27+15;
    %rez(3) = 1/(x^2+y^2+1) - 1.1^(-(x^2+y^2));
    
    %rez(1) = x;
    %rez(2) = (1+y)/x;

    rezultat = rez;