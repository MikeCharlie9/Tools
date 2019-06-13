#include<stdio.h>
#define N 9
int sdmatrix[9][9]={{0}
				/*{4,0,0,0,7,0,0,0,6},
				{0,0,3,0,0,0,1,0,0},
				{0,0,0,0,9,0,0,0,0},
				{9,0,1,7,0,3,8,0,2},
				{0,0,0,5,0,2,0,0,0},
				{8,0,0,0,1,0,0,0,3},
				{3,0,5,0,2,0,9,0,8},
				{2,0,0,4,0,8,0,0,1},
				{0,0,4,9,5,6,2,0,0}*/ 
				};
//ÊäÈë
void input(int [N][N]); 
//´òÓ¡¾ØÕó 
void print(int [N][N]); 
//¼ÆËã¸ÃÔªËØÊôÓÚÄÄÒ»¿é
int count_block(int,int); 
//°´´Ó×óµ½ÓÒ´ÓÉÏµ½ÏÂµÄË³Ðò±éÀú
void next(int &,int &,int &,int &);
 //¶Ô¼¸¸ö¸¨Öú¾ØÕó½øÐÐ³õÊ¼»¯
void initial(int [N][N],int [N][N],int [N][N],int [N][N]); 
//Ìî³äÖµ---µÝ¹éËã·¨ 
int fill(int,int,int [N][N],int [N][N],int [N][N],int [N][N]); 
//Ö÷º¯Êý 
int main()
{
	//char *fixed ="007409031010037080000800402420005009070000000300004010003598000006000048800000700"; 
	again:printf("*\n");
	input(sdmatrix);
	printf("**********initial**********\n");
	print(sdmatrix);
	printf("**********result**********\n");
	int r[N][N] = {{0}};
    int c[N][N] = {{0}};
    int b[N][N] = {{0}};
    initial(sdmatrix,r,c,b);
	fill(0,0,sdmatrix,r,c,b);
	printf("press 'c' to continue or other key to exit:");
	char nextstep;
	while((nextstep=getchar())=='\n');
	if(nextstep=='c')goto again;
	else return 0;
}

//ÊäÈë
void input(int a[N][N])
{
	printf("Çë°´ÕÕ´Ó×óµ½ÓÒ´ÓÉÏµ½ÏÂµÄË³ÐòÊäÈëÒ»¸öÊý¶À\n");
	printf("Êý¶ÀÖÐ¿ÕÎ»ÇëÓÃ0Ìî³ä\n");
	printf("Ã¿ÊäÈëÒ»¸öÊýÇëÇÃ¿Õ¸ñ»ò»Ø³µ\n");
	printf("½¨ÒéÃ¿ÊäÈë9¸öÇÃÒ»¸ö»Ø³µÒÔ±ã¼ì²é\n");
	int i,j;
	for(i=0;i<N;i++)
		for(j=0;j<N;j++)
			scanf("%d",&a[i][j]); 
} 
//´òÓ¡¾ØÕó 
void print(int a[9][9])
{
	int i,j;
	for(i=0;i<9;i++)
	{
		if(i==3||i==6)printf("---------------------\n");
		for(j=0;j<9;j++)
			{
				if(j==3||j==6)printf("| ");
				if(a[i][j])printf("%d ",a[i][j]);
				else printf("  ",a[i][j]);
			}
		printf("\n");
	}
}
//¼ÆËã¸ÃÔªËØÊôÓÚÄÄÒ»¿é 
int count_block(int hang,int lie)
{
	return hang/3*3+lie/3;
}
//°´´Ó×óµ½ÓÒ´ÓÉÏµ½ÏÂµÄË³Ðò±éÀú 
void next(int &a,int &b,int &c,int &d)
{
	if(b==8)
	{
		d=0;
		c=1+a;
	}
	else
	{
		c=a;
		d=1+b;
	}
}
//¶Ô¼¸¸ö¸¨Öú¾ØÕó½øÐÐ³õÊ¼»¯ 
void initial(int matrix[N][N],int hang[N][N],int lie[N][N],int block[N][N])
{
	int i,j;
	for(i=0;i<N;i++)
		for(j=0;j<N;j++)
			if(matrix[i][j])
			{
				hang[i][matrix[i][j]-1]=1;
				lie[j][matrix[i][j]-1]=1;
				block[count_block(i,j)][matrix[i][j]-1]=1;
			}
}
//Ìî³äÖµ---µÝ¹éËã·¨ 
int fill(int hang,int lie,int matrix[N][N],int r[N][N],int c[N][N],int b[N][N])
{
	if(matrix[hang][lie])
	{
		if(hang==N-1&&lie==N-1)
		{
			printf("\n"); 
			print(matrix);
			return 0;
		}			
		int nhang,nlie;
		next(hang,lie,nhang,nlie);
		fill(nhang,nlie,matrix,r,c,b);
		return 0;
	}
	else
	{
		int i;
		for(i=1;i<=N;i++)
		{
			if(r[hang][i-1]!=1&&c[lie][i-1]!=1&&b[count_block(hang,lie)][i-1]!=1)
			{
				matrix[hang][lie]=i;
				if(hang==N-1&&lie==N-1)
				{
					printf("\n"); 
					print(matrix);
					return 0;
				}
				r[hang][i-1]=1;
				c[lie][i-1]=1;
				b[count_block(hang,lie)][i-1]=1;
				int nexthang,nextlie;
				next(hang,lie,nexthang,nextlie);
				fill(nexthang,nextlie,matrix,r,c,b);
				r[hang][i-1]=0;
				c[lie][i-1]=0;
				b[count_block(hang,lie)][i-1]=0;
				matrix[hang][lie]=0;
			}
		}
	}
}

