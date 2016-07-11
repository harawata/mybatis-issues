# mybatis-issues

This repository helps contributors to create [SSCCE](http://sscce.org) : Short, Self Contained, Correct (Compilable), Example.

## Create a new repo using template

1. Fork this repository on GitHub.
1. Clone your fork to local.

  ```sh
$ git clone git@github.com:YOUR_ACCOUNT/mybatis-issues.git
```
1. In the cloned repo, execute the gradle task with a project name.

  ```sh
# create a new directory with name gh-123
$ gradle create -PprojectName=gh-123
```
1. Make changes so that the project can reproduce your problem.
1. Commit your changes and push them to your forked repository.

  ```sh
$ git add -A
$ git commit -m "Some commit message"
$ git push
```
1. Let us know the URL of your repository.
