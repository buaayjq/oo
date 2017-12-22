#include <stdio.h>
#include <string.h>
#include <ctype.h>

void spacedel(char string[]){//É¾³ý¿Õ¸ñ
     char *p;
     int l,i;
     l=strlen(string);
     for(i=0;i<l;i++)
     {for(p=string;*p;p++)
     if(*p==' ')
     strcpy(p,p+1);}
}

int add(){

}

int sub(){

}
/*typedef struct Poly{
    int coeff[];
    int degree[];
}*/
int main(){

	int i=0,j=-1,op=1;
	int coeff[10000]={0},degree[10000]={0},ci=0,di=0;//coeffÊÇÏµÊý£¬degreeÊÇÃÝ
	int p1=0,p2=0,t=1;  //ÓÃÓÚÌáÈ¡ÏµÊýºÍÃÝ
	int resultc[1000]={0},resultd[1000]={0},x=0,sum=0;
	char s[10000]={0},stack[10000]={0};
	gets(s);
	spacedel(s);

	for(i=0;s[i]!='\0';i++){
			if(s[i]!='{'){//²»ÒÔ{¿ªÍ·±¨´í
				printf("输入错误");
				return 0;
			}
			break;
	}

	for(i=0;s[i]!='\0';i++){
            if(s[i]=='+'){
                if(j==-1){
                    op=1;
                    if(s[i+1]!='{'){//
                        printf("输入错误");
                        return 0;
                    }
                }
                else{
                    stack[++j]=s[i];
                }

		}
		else if(s[i]=='-'){
			if(j==-1){
				op=-1;
				if(s[i+1]!='{'){
					printf("输入错误");
					return 0;
				}
			}
			else{
				stack[++j]=s[i];
			}
		}

		else{
			if(s[i]=='}'){
				if(stack[j]='{' && j==0){
					j--;
				}
				else{
					printf("输入错误");
					return 0;
				}
			}
			else if(s[i]!=')'){
				stack[++j]=s[i];
			}

			else if(s[i]==')'){
				if(s[i+1]==','){
					i++;
					if(s[i+1]!='('){
						printf("输入错误");
						return 0;
					}
				}
				else if(s[i+1]=='}'){
                        printf("输入错误");
						return 0;
				}
				else{
					printf("输入错误");
					return 0;
				}
				if(stack[j]=='('){
					printf("输入错误");
					return 0;
				}
				while(stack[j]!='(' && j>0){
					if(stack[j]==','){
						printf("输入错误");
						return 0;
						}
					else if(isdigit(stack[j])){
						while(isdigit(stack[j])){
							p2=p2+(stack[j]-'0')*t;
							t=t*10;
							j--;
						}
						t=1;
						if(stack[j]=='-'){
							p2=-p2;
							j--;
						}
						else if(stack[j]=='+'){
							j--;
						}
						if(stack[j]==','){
							j--;
							if(isdigit(stack[j])){
								while(isdigit(stack[j])){
									p1=p1+(stack[j]-'0')*t;
									t=t*10;
									j--;
								}
								t=1;
								if(stack[j]=='-'){
									p1=-p1;
									j--;
								}
								else if(stack[j]=='+'){
									j--;
								}
							}
							else{
								printf("输入错误");
								return 0;
							}
						}
						else{
							printf("输入错误");
							return 0;
						}
						if(stack[j]!='(' || j<1){
							printf("输入错误");
							return 0;
						}
						coeff[ci++]=p1*op;
						degree[di++]=p2;
						p1=0;
						p2=0;
					}

					else{
						printf("输入错误");
						return 0;
					}
				}

				if(stack[j]=='('){
					j--;
				}
			}
		}
	}
	if(j!=-1){
		printf("输入错误");
		return 0;
	}


	for(i=0;i<ci;i++){
		if(coeff[i]==0){
			continue;
		}
		else{
			sum=coeff[i];
			for(j=i+1;j<di;j++){
				if(degree[i]==degree[j]){
					sum+=coeff[j];
					coeff[j]=0;
				}
			}
			if(sum==0){

			}
			else{
				resultc[x]=sum;
				resultd[x++]=degree[i];
				sum=0;
			}
		}
	}

	//Êä³ö
	/*printf("计算结果系数为：");
	for(i=0;i<x;i++){
		printf("%d,",resultc[i]);
	}
	printf("\n计算结果次数为:");
	for(i=0;i<x;i++){
		printf("%d,",resultd[i]);
	}*/

	for(i=0;i<x;i++){
        printf("%d %d\n",resultc[i],resultd[i]);
	}


}

