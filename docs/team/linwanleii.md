---
layout: page
title: Lin Wan Lei's Project Portfolio Page
---

### Project: PharmHub

PharmHub - PharmHub is an order and patient tracking application for small remote pharmacist to implement an all-in-one application to improve precision and efficiency. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added A Detail information display to GUI.
  * What it does: Allows user to view more detailed information on order `viewo` and person `viewp`. Displays the information of most recent added/edited/deleted order or person.
  * Justification: This feature improves the product significantly because the user can view and analysis more in-depth information easy. Furthermore, displaying the orders or person after an action is done on them helps them check and rectify their actions.
  * Highlights: This enhancement affects how the whole application works and displays itself. It enabled a lot of future implementation that could be build upon to further enhance the application. The challenging part of the implementation was to determine the characteristic and the look of the display.
  * Credits: NIL

* **New Feature**: Added A Status for Orders.
  * What it does: A new component Status is being added to the order. The user can update the status base on their current progression with `updates`. Status is used mainly for tracking and filtering of orders. Status also has a chronological order to prevent mis-inputs.
  * Justification:  This feature improves the product as it allows the user to track their order to improve efficiency and also prevent orders from being forgotten. Furthermore, the restriction of chronological order further ensures no orders will be updated wrong and hence forgotten.
  * Highlights: This enhancement affects how the product displays itself. Each status has their own individual colours, hence easily identifiable. Additionally, every order must have a status, therefore enables a lot of future implementation.
  * Credits: NIL

* **New Feature**: Added A find order command.
  * What it does: Allows the user to find or filter orders base on either the order status or medicine or both. It then displays the filtered list on the list display.
  * Justification: This feature helps to improve productivity of the pharmacist as it enables them to filter the order list when there are multiple orders and allows them to identify specific orders easily.
  * Highlights: The feature not only allows order to be search base on individual component which are the status or medicine keywords, it also allows both to be used as predicates. This feature enables a lot of future implementation as there can be multiple ways of searching.
  * Credits: NIL

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=linwanleii&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=LinWanLeii&tabRepo=AY2324S1-CS2103T-W08-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Team lead for the group.
  * Managed meetings, discussion and task allocation for the team throughout the project.

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
  * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * User Guide:
    * Added documentation for the features `addo`, `deleteo`, `updates` and `findo` [\#124](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/124/files)
    * In charge of `Table of Content`, `Quick Start` and `Trouble Shooting`
    * Added new category called `Definition` and `Navigation`
  * Developer Guide:
    * Added implementation details of the `findo` and `addo` feature.
    * Managed the Table of content.
    * Updated the storage component.
    * Updated and added all use cases.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#184](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/184), [\#167](https://github.com/AY2324S1-CS2103T-W08-4/tp/pull/167)
