# Reason : 

**왜 해?**

- 노트북에 VMBox 설치할 용량이 없어서, 우분투를 쓰려고 노트북 2개 가지고 다님
- 웹 우분투가 느림
- 클라우드 서비스를 사용하고 싶음
    - Azure student 계정이 있음
# Action : 

**뭘 했어?**

- Azure 리소스 그룹 만들기
- Azure VM 리소스 만들기
- Azure VM 리소스 에서 Ubuntu 사용
- 학교 리눅스운영체제응용 수업 실습
    - Ubuntu 에서 명령어 실습
# Insight : 

**뭘 느꼈어?**

- 하고 싶으면 다 된다
- 클라우드는 편하다
## 잘한점

- 클라우드 사용에 도전한것
## 개선점

- 왜 되는지 어떤 명령어 인지 알고 사용할것
## 배운점

- Azure VM에서 Azure CLI를 사용하는 SSH로 시작하면  Azure cloud Shell 에서 시작 되는구나
    - 왜? : 
- VM에서 Ubuntu를 설치해서 시작하면 bourn Shell를 처음에 사용하는구나
    - 왜? : 
- bash shell을 쓰려면 바꿔줘야 하는구나
- /home/azureuser와 /home/내계정이름 으로 구성되어 있구나
- /home/azureuser은 들어갈수 없다
    ```shell
 $  cd azureuser
-sh: 19: cd: can't cd to azureuser
```
    - 왜? : 
- 