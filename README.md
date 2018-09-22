# InterCity Car System (to be named)

## TravisCI Build Status

[![Build Status](https://travis-ci.com/ECSE321-Fall2018/t08.svg?token=atEt1SppUvzajjRzBkhC&branch=master)](https://travis-ci.com/ECSE321-Fall2018/t08)

## Using this Git Repository

### Cloning

To clone this repository, enter `git clone https://github.com/ECSE321-Fall2018/t08` in the desired directory.

### Pulling

**Before pushing any changes or creating a new branch, it is highly recommended that you pull.**

**Before pulling, make sure that you are in the master branch.**
To switch to the master branch, enter `git checkout master`.
Then, you can use `git pull origin master` to pull from GitHub.

### Making a New Branch

All changes to the repository should *not* be made on the master branch. Rather, you should make your own branch. To create a new branch, enter `git branch <branch-name>` (without the '<>'). To actually switch to this branch (as you will still be on the master branch), enter `git checkout <branch-name>`. To list all the branches, simply enter `git branch`.

Once you have created all of your changes on your branch, you can push to GitHub as described below.

### Committing

You can use `git commit -m "Commit Message"` to commit. Note that the commit message should ideally be something like `Closes #3` if you are closing issue number 3.

### Pushing 

To push your commits to GitHub from your branch, use the command `git push -u origin <branch-name>`. This should automatically display a pull request on GitHub. Go online to the GitHub repository and there should be an option for you to create your pull request from your push. This pull request will then be reviewed by another member before you can merge this branch with the master.
