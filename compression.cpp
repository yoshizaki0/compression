#include <bits/stdc++.h>
#include <iostream>
using namespace std;
long long  temp[26];/*呼び出し先がカウントしたアルファベットを格納*/
int pointer=0;
string text;
long long length(){
    long long number=0;
    while(isdigit(text[pointer])!=0){
        number=number*10;
        number+=text[pointer]-'0';
        pointer++;
    }
    return number;
}
void mult(){
    long long num=0;
    int long long subalf[26];
    int pretype=-1;/*直前のトークンが　0　数字、　1 アルファベット ,2　()   */
    for(int i=0;i<26;i++){
        subalf[i]=0;
    }
    while(pointer<(int)text.size()){
        if(isdigit(text[pointer])!=0){
            num=length();
            pretype=0;
        }else if(text[pointer]=='('){
            pointer++;
            mult();
            /*帰還後の処理*/
            if(pretype==0){
                for(int i=0;i<26;i++){
                    subalf[i]+=num*temp[i];
                }
            }else{
                for(int i=0;i<26;i++){
                    subalf[i]+=temp[i];
                }
            }
            pretype=2;
            
        }else if(text[pointer]==')'){
            pointer++;
              for(int i=0;i<26;i++){
                temp[i]=subalf[i];
              }
              return;
            
        }else{/*アルファベット確定*/
            
                if(pretype==0){
                    subalf[text[pointer]-97]+=num;
                }else{
                    subalf[text[pointer]-97]++;
                }
                 pretype=2;
                 pointer++;
        }
    }
    for(int i=0;i<26;i++){
                temp[i]=subalf[i];
    }
              return;
    
}



int main(void){
    for(int i=0;i<26;i++) temp[i]=0;
    cin >>text;
    mult();
    cout<< "a "<<temp[0]<<endl;
    cout<< "b "<<temp[1]<<endl;
    cout<< "c "<<temp[2]<<endl;
    cout<< "d "<<temp[3]<<endl;
    cout<< "e "<<temp[4]<<endl;
    cout<< "f "<<temp[5]<<endl;
    cout<< "g "<<temp[6]<<endl;
    cout<< "h "<<temp[7]<<endl;
    cout<< "i "<<temp[8]<<endl;
    cout<< "j "<<temp[9]<<endl;
    cout<< "k "<<temp[10]<<endl;
    cout<< "l "<<temp[11]<<endl;
    cout<< "m "<<temp[12]<<endl;
    cout<< "n "<<temp[13]<<endl;
    cout<< "o "<<temp[14]<<endl;
    cout<< "p "<<temp[15]<<endl;
    cout<< "q "<<temp[16]<<endl;
    cout<< "r "<<temp[17]<<endl;
    cout<< "s "<<temp[18]<<endl;
    cout<< "t "<<temp[19]<<endl;
    cout<< "u "<<temp[20]<<endl;
    cout<< "v "<<temp[21]<<endl;
    cout<< "w "<<temp[22]<<endl;
    cout<< "x "<<temp[23]<<endl;
    cout<< "y "<<temp[24]<<endl;
    cout<< "z "<<temp[25]<<endl;
    
    
}