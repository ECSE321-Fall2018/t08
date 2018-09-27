# RideShare

## TravisCI Build Status
To check the details on why our TravisCI build succeeded or failed:

[![Build Status](https://travis-ci.com/ECSE321-Fall2018/t08.svg?token=atEt1SppUvzajjRzBkhC&branch=master)](https://travis-ci.com/ECSE321-Fall2018/t08)

## How Everything Works
If you're clueless about what's going on, here's a summary.

- **GitHub:** Where our code is stored.
- **Git:** Where you copy GitHub code to computer and upload it back to GitHub.
- **TravisCI:** It checks that the code you uploaded to GitHub is okay (to a certain extent).
- **Maven:** It's a build tool. It compiles and tests our Java code for us.
- **Gradle:** We don't need it phew.
- **Spring:** Helps us send HTTP Requests (e.g. create, update, delete user) to our website.
- **Heroku:** What we are using to host our website and our backend.

## How to Use this GitHub Repository

### How to Clone
To clone this repository, enter `git clone https://github.com/ECSE321-Fall2018/t08` in the desired directory. It will create a `t08` folder containing our project code.

### How to Make Changes to Our Project

#### Quick Guide
1. Before making changes or switching branches, make sure to pull.
2. Do not make changes in the master branch.
    1. Instead, make a new branch: `git branch <branch-name>`
    2. Go to new branch: `git checkout <branch-name>`
    3. List all branches: `git branch`
2. Only pull from the master branch.
    1. Go to master branch: `git checkout master`
    2. Pull from master branch: `git pull origin master`
3. To commit changes: `git commit -m "Commit Message"`
    1. If you want ot close issue 3 for example, your commit message should include `Closes #3`.
4. To push changes to GitHub: `git push -u origin <branch-name>`
5. To merge pushed changes with master branch, create a pull request and get one person to approve your request.

#### Long Guide
1. Go to the ["Issues" tab on GitHub](https://github.com/ECSE321-Fall2018/t08/issues).
2. Look for an issue to fix or create your own issue (press "New issue").
    1. If you are fixing another person's issue:
        1. You can leave a comment if you have questions for the guy (or girl) who created the issue.
        2. Go directly to step 3.
    2. If you are creating your own issue:
        1. You must add the label/milestone sprint1, sprint2, or sprint3. Otherwise, the TA won't be able to check our issues.
        2. Also, add a priority label if necessary:
            1. If it needs to be fixed in the next 24 hours and it's really important, use the "Priority 1 [Urgent!]" label.
            2. If it's really important but we have time, use the "Priority 2" label.
            3. If it's somewhat important, use the "Priority 3" label.
        2. You can add other labels like "bug", "help wanted", or "question" to make your issue more helpful to others.
        3. If you want to include a comment for your issue, it can be in the form of a user story: `As a <type of user>, I would like <feature description> so that <rationale>.`
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
8. Go to the ["Pull requests" tab on GitHub](https://github.com/ECSE321-Fall2018/t08/pulls). Press the "Create pull request" button for your branch.
9. Travis CI will tell you if your build succeeded or failed in the Pull requests tab.
10. If it failed, go back to step 5.
11. If it succeeded, get someone to approve it in "Pull requests" tab.
12. If you didn't automatically close the issue you were trying to fix in step 6.1.1, you can do that manually right now. Go to the "Issues" tab, click the issue you fixed, then press the "Close issue" button.
13. Switch to master branch: `git checkout master`
14. Pull from master branch: `git pull origin master`

## Maven
Whenever you make changes to our Java files and want to run the code, run this command on the terminal:
```bash
mvn test
```
It will compile, run, and test the code. You can see the output and possible error messages.

## Spring

## Heroku
How to access our website:
1. Go to [Heroku](https://heroku.com) and login.
2. Click on "rideshare08".
3. Press "Open App".
4. Enjoy the beauty of our website!

How to access our database code:
1. Install Heroku CLI.
2. heroku git:clone -a rideshare08 (not in our `t08` Git repository!)