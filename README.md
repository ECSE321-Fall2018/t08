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

### How to Make Changes to Our Project

1. Go to the ["Issues" tab on GitHub](https://github.com/ECSE321-Fall2018/t08/issues).
2. Look for an issue to fix or create your own issue (press "New issue").
    1. If you are fixing another person's issue:
        1. You can leave a comment if you have questions for the guy who created the issue.
        2. Go directly to step 3.
    2. If you are creating your own issue:
        1. You must add the label/milestone sprint1, sprint2, or sprint3. Otherwise, the TA won't be able to check our issues.
        2. Also, add a priority label if necessary:
            1. If it needs to be fixed in the next 24 hours and it's really important, use the "Priority 1 [Urgent!]" label.
            2. If it's really important but we have time, use the "Priority 2" label.
            3. If it's somewhat important, use the "Priority 3" label.
        2. You can add other labels like "bug", "help wanted", or "question" to make your issue more helpful to others.
        3. If you want to include a comment for your issue, it should be in the form of a user story: `As a <type of user>, I would like <feature description> so that <rationale>.`
        4. Example user story: `As a course instructor, I would like to add content to a course so that enrolled students can access it on demand`.
3. Create branch: `git branch <branch-name>`
4. Go to branch: `git checkout <branch-name>`
5. **Fix the issue (this is the step where you actually make changes to the code in our project!).**
6. Commit your code:
    1. Add to commit: `git add .`
    2. Undo step 1: `git reset` 
    3. Commit: `git commit -m "Commit Message"`
        1. In the `"Commit Message"`, you can automatically close an issue by including the phrase `closes #4` (in this case `#4` is the ID number of the issue, you can find this number in the "Issues" tab on GitHub).
        2. Aside from `closes`, you can use the keywords: `close`, `closed`, `fix`, `fixes`, `fixed`, `resolve`, `resolves`, or `resolved`. It can be capitalized too.
7. Push your code to online master branch: `git push -u origin <your-branch-name>` (DO NOT push from your local master branch).
8. Check the ["Pull requests" tab on GitHub](https://github.com/ECSE321-Fall2018/t08/pulls). Travis CI will tell you if your build succeeded or failed in the Pull requests tab.
9. If it failed, go back to step 5.
10. If it succeeded, get someone to approve it in "Pull requests" tab.
11. If you didn't automatically close the issue you were trying to fix in step 6.1.1, you can do that manually right now. Go to the "Issues" tab, click the issue you fixed, then press the "Close issue" button.
12. Switch to master branch: `git checkout master`
13. Pull from master branch: `git pull origin master`

I know process is tedious and annoying, but we need to do this to get a good grade for our backlog (a todo list of issues).