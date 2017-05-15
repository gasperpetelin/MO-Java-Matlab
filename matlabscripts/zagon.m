 function rezultat=zagon(vhod)

    x = vhod(1);
    y = vhod(2);
 
    rez(1) = x^2-y;% + (vhod(2)-3)^2;
    rez(2) = -.5*x-y-1;
    rez(3) = x-y;
    rez(4) = y-2*x;
%     rez(5) = vhod(2);
%     rez(6) = 7;
%     rez(7) = vhod(3)/vhod(1);
%     rez(8) = 3;
%     rez(9) = vhod(3);
%     rez(10) = 3.14;
    rezultat = rez;