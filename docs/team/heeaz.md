---
layout: page
title: Immanuel Chia's Project Portfolio Page
---

### Project: Pharmhub

PharmHub is a desktop application for pharmacists to track orders. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

**New Feature**: Added the ability to undo/redo previous commands.

* What it does: allows the user to undo up to 30 previous data-changing commands one at a time. Preceding undo commands can be reversed by using the redo command.

* Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them. This is also vital to prevent accidental loss of data, which can have catastrophic consequences given the sensitive nature of medicine order management.

* Highlights: This enhancement affects existing commands and commands to be added in future. 

* Credits: None

**New Feature**: Added the ability to view a person/ medication detail in an info display via `viewp` or `viewo`

* What it does: Allows the user to see more details about a person/ order in an expanded info display.

* Justification: When it comes to patient and medication details, there are a lot of details to capture, and is unfeasible to capture all of the information regarding a patient/ order in a small list card. This detailed view allows users to see condensed details in the listview, and see full details in the info panel, should they wish to. This ability to peg information of a patient/ order onto the info display also gives users greater versatility with navigation, ie. users are able to list all orders *and* the details of a specific patient at the same time.

* Highlights: This enhancement affects existing commands and commands to be added in future.

* Credits: None

**Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=heeaz&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

**Project management**:

* Managed releases `v1.3` - `v1.4` (2 releases) on GitHub
* Managed issues on github: scoping and defining taskings that need to be done, including consolidating PE-D issues

* **Enhancements to existing features**:

* Enhanced listPanel GUI with onclick functionalities [#90](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/90)
* Enhanced add, edit and delete feedback by displaying the affected patient/ order on the info display [#90](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/90)


**Documentation**:

* User Guide:

* Added documentation for the features `viewp`, `viewo`, `undo`, and `redo`

* Developer Guide:

* TODO

**Community**:

* PRs reviewed (with non-trivial review comments): [\#65](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/65), [\#104](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/104), [\#102](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/102), [\#53](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/53)

* Reported bugs and suggestions for other teams in the class: None
