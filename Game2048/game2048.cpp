#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <iostream>

#define NMAX 4

#define PROP 70//the probability of 2

#define UP 8
#define DOWN 2
#define LEFT 4
#define RIGHT 6

using namespace std;
int board[NMAX][NMAX];
long score=0;
bool user=false;//true: you can cheat;false: you cannot;

class Game2048
{
	public :
		void startMenu();
		void initialBoard();
		void showBoard();
		int readControl();
		void moveNum();
		void getRandPosition(int &row,int &col);
		int generateNum();
		int judge();
	private :
		void cheat();
};


void Game2048::startMenu()
{
	cout<<"********************************************************************************\n";
	cout<<"Introduction for 'Game2048'\n";
	cout<<"Using direction key to control direction,ESC to exit.\n";
	cout<<"The score you get is the sum of the blocks you eliminated.\n";
	cout<<"If the board is full of block, and none of the them can be eliminated, you fail!\n";
	cout<<"********************************************************************************\n";
	cout<<"Press 'Enter' to start the game\n";
	char input=getch();
	if(input==0x20)user=true;
	else user=false;
}


int Game2048::generateNum()
{
	int r=rand()%100;
	if(r<PROP)return 2;
	else return 4;
}


void Game2048::getRandPosition(int &row,int &col)
{
	while(1)
	{

		row=rand()%NMAX;
		col=rand()%NMAX;
		if(board[row][col]==0)break;
		
	}
}



void Game2048::moveNum()
{
	int direction=readControl();
	//cout<<direction<<"\n";
	switch(direction)
	{
		case UP:
			for(int hang=1;hang<NMAX;hang++)
			{
				for(int move=hang;move>=1;move--)
				{
					for(int lie=0;lie<NMAX;lie++)
					{
						if(board[move-1][lie]==0)
						{
							board[move-1][lie]=board[move][lie];
							board[move][lie]=0;
						}
						else if(board[move-1][lie]==board[move][lie])
						{
							board[move-1][lie]*=2;
							score+=board[move-1][lie];
							board[move][lie]=0;
						}
					}
				}
			}
			break;
		case DOWN:
			for(int hang=NMAX-2;hang>=0;hang--)
			{
				for(int move=hang;move<=NMAX-2;move++)
				{
					for(int lie=0;lie<NMAX;lie++)
					{
						if(board[move+1][lie]==0)
						{
							board[move+1][lie]=board[move][lie];
							board[move][lie]=0;
						}
						else if(board[move+1][lie]==board[move][lie])
						{
							board[move+1][lie]*=2;
							score+=board[move+1][lie];
							board[move][lie]=0;
						}
					}
				}
			}
			break;
		case LEFT:
			for(int lie=1;lie<NMAX;lie++)
			{
				for(int move=lie;move>=1;move--)
				{
					for(int hang=0;hang<NMAX;hang++)
					{
						if(board[hang][move-1]==0)
						{
							board[hang][move-1]=board[hang][move];
							board[hang][move]=0;
						}
						else if(board[hang][move-1]==board[hang][move])
						{
							board[hang][move-1]*=2;
							score+=board[hang][move-1];
							board[hang][move]=0;
						}
					}
				}
			}
			break;
		case RIGHT:
			for(int lie=NMAX-2;lie>=0;lie--)
			{
				for(int move=lie;move<=NMAX-2;move++)
				{
					for(int hang=0;hang<NMAX;hang++)
					{
						if(board[hang][move+1]==0)
						{
							board[hang][move+1]=board[hang][move];
							board[hang][move]=0;
						}
						else if(board[hang][move+1]==board[hang][move])
						{
							board[hang][move+1]*=2;
							score+=board[hang][move+1];
							board[hang][move]=0;
						}
					}
				}
			}
			break;
		case 27:
			exit(0);
		case -1:
			if(user)cheat();
			break;
	}
}


void Game2048::cheat()
{
	cout<<"cheatting!\n";
	cout<<"1:genertate 2048 on row:3 col:0;\n";
	cout<<"2:change the score;\n";
	cout<<"3:clear the number you don't want;\n";
	cout<<"4:change the value of a block;\n";
	cout<<"input your choice:";
	int choice;
	cin>>choice;
	switch(choice)
	{
		case 1:
			board[3][0]=2048;
			break;
		case 2:
			cout<<"input the score:";
			cin>>score;
			break;
		case 3:
			int row1,col1;
			cout<<"input the row:";
			cin>>row1;
			cout<<"input the col:";
			cin>>col1;
			board[row1][col1]=0;
			break;
		case 4:
			int row2,col2;
			cout<<"input the row:";
			cin>>row2;
			cout<<"input the col:";
			cin>>col2;
			cout<<"input the value:";
			cin>>board[row2][col2];
	}
}


int Game2048::readControl()
{
	char ch=getch();
	int control;
	if(ch==-32)
	{
		ch=getch();
		switch(ch)
		{
			case 72:control=UP;break;//UP
			case 80:control=DOWN;break;//DOWN
			case 75:control=LEFT;break;//LEFT
			case 77:control=RIGHT;break;//RIGHT
			default :control=0;
		}
	}
	else if(ch==0x20)
	{
		control=-1;
	}
	else if(ch==27)
	{
		control=27;//exit;
	}
	else control=0;
	return control;
}



void Game2048::initialBoard()
{	
	for(int i=0;i<NMAX;i++)
	{
		for(int j=0;j<NMAX;j++)
		{
			board[i][j]=0;
		}	
	}
	int row,col;
	getRandPosition(row,col);
	board[row][col]=generateNum();
	
}



void Game2048::showBoard()
{
	cout<<"  ";
	for(int i=1;i<NMAX*4;i++)
	{
		cout<<"-";
		if(i%4==0)cout<<" ";
	}
	cout<<"\n";
	for(int i=0;i<NMAX;i++)
	{
		cout<<"| ";
		for(int j=0;j<NMAX;j++)
		{
			if(board[i][j]==0)cout<<"    ";
			else printf("%4d", board[i][j]);
			cout<<" ";
		}
		cout<<"|";
		
		cout<<"\n";
	}
	cout<<"  ";
	for(int i=1;i<NMAX*4;i++)
	{
		cout<<"-";
		if(i%4==0)cout<<" ";
	}
	cout<<"\n";
}


int Game2048::judge()
{
	int count=0;
	for(int i=0;i<NMAX;i++)
	{
		for(int j=0;j<NMAX;j++)
		{
			if(board[i][j]!=0)count++;
		}
	}
	return (NMAX*NMAX-count);
}


int main()
{
	Game2048 newGame;
	newGame.startMenu();
	system("cls");
	newGame.initialBoard();
	cout<<"score:"<<score<<"\n";
	newGame.showBoard();
	while(1)
	{
		newGame.moveNum();
		if(newGame.judge()==0)
		{
			cout<<"failed!";
			break;
		}
		system("cls");
		int row,col;

		newGame.getRandPosition(row,col);
		board[row][col]=newGame.generateNum();
		cout<<"score:"<<score<<"\n";
		newGame.showBoard();
	}
	
	return 0;
}
