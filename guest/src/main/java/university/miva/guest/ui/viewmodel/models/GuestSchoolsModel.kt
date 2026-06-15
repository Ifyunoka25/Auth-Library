package university.miva.guest.ui.viewmodel.models

import kotlinx.serialization.Serializable
import university.miva.designsystem.R as DesignSystemR


val facultyPageImageMap = mapOf(
    "School of Computing" to DesignSystemR.drawable.ic_sch_of_comp_sc_fc_image,
    "School of Management and Social Sciences" to DesignSystemR.drawable.ic_sch_of_mgt_and_ss_fc_image,
    "School of Communication and Media" to DesignSystemR.drawable.ic_sch_of_comm_and_mass_st_fc_image,
    "School of Communication & Media Studies" to DesignSystemR.drawable.ic_sch_of_comm_and_mass_st_fc_image,
    "School of Allied Health Sciences" to DesignSystemR.drawable.ic_sch_of_allied_health_sc_fc_image,
)

val facultyLogoMap = mapOf(
    //Undergraduates only
    "School of Computing" to DesignSystemR.drawable.ic_school_of_computing_logo,
    "School of Management and Social Sciences" to DesignSystemR.drawable.ic_school_of_mgt_and_social_sciences,
    "School of Management & Social Sciences" to DesignSystemR.drawable.ic_school_of_mgt_and_social_sciences,
    "School of Communication and Media" to DesignSystemR.drawable.ic_school_of_comm_and_media_logo,
    "School of Communication and Media Studies" to DesignSystemR.drawable.ic_school_of_comm_and_media_logo,
    "School of Communication & Media Studies" to DesignSystemR.drawable.ic_school_of_comm_and_media_logo,
    "School of Allied Health Sciences" to DesignSystemR.drawable.ic_school_of_allied_health_sciences_logo,
)

val programmeRequirementsInfoMap = mapOf(
    "Master of Business Administration (MBA)" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for an MBA at Miva University:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "Entry Requirements for MBA",
                subTitle = "",
                directEntryBulletPoints =
                    listOf(
                        "Five O'level credit passes including English Language and Mathematics.",
                        "Bachelor's degree in any field from a recognised university, with a minimum of second class lower division.",
                        "HND graduates with a minimum of upper credit may be considered.",
                        "Candidates are required to have a minimum of one year post-graduation work experience. Applicants with lower academic qualifications may generally need to demonstrate longer duration of work experience.",
                    ),
                informationIntro = "",
                informationEnd = "",
                informationBulletPoints = emptyList()
            ),
        ),
    ),
    "Master of Public Administration (MPA)" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for an MBA at Miva University:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "Entry Requirements for MBA",
                subTitle = "",
                directEntryBulletPoints =
                    listOf(
                        "Five O'level credit passes including English Language and Mathematics.",
                        "Bachelor’s degree with a minimum of second class (Lower Division) in any field.",
                        "HND graduates require a Postgraduate Diploma to be eligible.",
                        "Candidates are expected to have a minimum of 2 years post-bachelor’s relevant work experience. Applicants with lower academic results may generally need to demonstrate a longer duration of work experience.",
                    ),
                informationIntro = "",
                informationEnd = "",
                informationBulletPoints = emptyList(),
            ),
        ),
    ),
    "Master of Information Technology (MIT)" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for an MIT at Miva University:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "Entry Requirements for MIT",
                subTitle = "",
                directEntryBulletPoints =
                    listOf(
                        "A bachelor’s degree in Computer Science, IT, Cybersecurity, Software Engineering, Data Science, or related fields with a minimum Second Class (Lower Division) and a CGPA of at least 2.5 on a 5.0 scale.",
                        "A Postgraduate Diploma (PGD) in a related area with a CGPA of at least 3.0.",
                        "A minimum of 2 years post-bachelor’s relevant work experience is required. Applicants with lower academic results may need to demonstrate additional work experience.",
                    ),
                informationIntro = "",
                informationEnd = "",
                informationBulletPoints = emptyList()
            ),
        ),
    ),
    "Master of Public Health (MPH)" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for an MPH at Miva University:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "Entry Requirements for MPH",
                subTitle = "",
                directEntryBulletPoints =
                    listOf(
                        "Five O'level credit passes including English Language and Mathematics.",
                        "Bachelor’s degree with a minimum of Second Class (Lower Division) in a relevant field.",
                        "HND graduates require a Postgraduate Diploma to be eligible.",
                        "A minimum of 2 years post-bachelor’s relevant work experience is required. Applicants with lower academic results may need to demonstrate a longer duration of work experience.",
                    ),
                informationIntro = "",
                informationEnd = "",
                informationBulletPoints = emptyList()
            ),
        ),
    ),
    "BSc. Computer Science" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva University",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Regularisation.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Physics",
                    "Two other science-related subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes in science subjects including Mathematics.",
                        "NCE merit passes in Mathematics and one other Science subject.",
                        "ND lower credit in Computer Science or other Mathematics/Computing/Physics/Electronics based programmes.",
                        "Very good passes in three (3) JUPEB subjects: Physics, Mathematics, Chemistry or Biology.",
                        "'A' Level passes chosen from English Language, Mathematics, Environmental Science, Biology, Chemistry, Physics, Literature-in-English, Government, History, Economics, Geography, Further Mathematics, Technical Drawing, Visual Arts, Computer Studies, Information Technology, Civics, and French.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
                informationIntro = "",
                informationBulletPoints = emptyList(),
                informationEnd = ""
            ),
        ),
    ),
    "BSc. Cybersecurity" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva University",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Regularisation.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Physics",
                    "Two other science-related subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes in science subjects including Mathematics.",
                        "NCE merit passes in Mathematics and one other Science subject.",
                        "ND lower credit in Computer Science or other Mathematics/Computing/Physics/Electronics based programmes.",
                        "Very good passes in three (3) JUPEB subjects: Physics, Mathematics, Chemistry or Biology.",
                        "'A' Level passes chosen from English Language, Mathematics, Environmental Science, Biology, Chemistry, Physics, Literature-in-English, Government, History, Economics, Geography, Further Mathematics, Technical Drawing, Visual Arts, Computer Studies, Information Technology, Civics, and French.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
                informationIntro = "",
                informationBulletPoints = emptyList(),
                informationEnd = ""
            ),
        ),
    ),
    "BSc. Data Science" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva University",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Physics",
                    "Two other science-related subjects"
                ),
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Regularisation.",
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes in science subjects including Mathematics.",
                        "NCE merit passes in Mathematics and one other Science subject.",
                        "ND lower credit in Computer Science or other Mathematics/Computing/Physics/Electronics based programmes.",
                        "Very good passes in three (3) JUPEB subjects: Physics, Mathematics, Chemistry or Biology.",
                        "'A' Level passes chosen from English Language, Mathematics, Environmental Science, Biology, Chemistry, Physics, Literature-in-English, Government, History, Economics, Geography, Further Mathematics, Technical Drawing, Visual Arts, Computer Studies, Information Technology, Civics, and French.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
                informationIntro = "",
                informationBulletPoints = emptyList(),
                informationEnd = ""
            ),
        ),
    ),
    "BSc. Software Engineering" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva University",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Regularisation.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Physics",
                    "Two other science-related subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes in science subjects including Mathematics.",
                        "NCE merit passes in Mathematics and one other Science subject.",
                        "ND lower credit in Computer Science or other Mathematics/Computing/Physics/Electronics based programmes.",
                        "Very good passes in three (3) JUPEB subjects: Physics, Mathematics, Chemistry or Biology.",
                        "'A' Level passes chosen from English Language, Mathematics, Environmental Science, Biology, Chemistry, Physics, Literature-in-English, Government, History, Economics, Geography, Further Mathematics, Technical Drawing, Visual Arts, Computer Studies, Information Technology, Civics, and French.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
                informationIntro = "",
                informationBulletPoints = emptyList(),
                informationEnd = ""
            ),
        ),
    ),
    "BSc. Information Technology" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva University",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "A copy of your O’Level result\n" +
                        "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Regularisation.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Physics",
                    "Two other science-related subjects"
                )
            ),
        ),
    ),
    "BSc. Business Management" to ProgrammeRequirementsInfo(
        description = "The entry requirements for a bachelor’s degree in Business Management at Miva Open University are stated below:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "A copy of your O’Level result\n" +
                        "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Regularisation.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Physics",
                    "Two other science-related subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes chosen from Economics, Accounts, Business Management, Government, Geography or Statistics.",
                        "Lower credit pass in ND/NCE in related fields.",
                        "Very good passes in three (3) JUPEB subjects: Economics, and any two (2) of Financial Accounting, Business Management, Government Geography and Mathematics.",
                        "Complete pass in the Intermediate ICAN examination or equivalent (AAT, ACCA, ATS final certificate).",
                        "AIB or other professional qualifications in relevant programmes.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
                informationIntro = "",
                informationBulletPoints = emptyList(),
                informationEnd = ""
            ),
        ),
    ),
    "BSc. Economics" to ProgrammeRequirementsInfo(
        description = "The entry requirements for a bachelor’s degree in Economics at Miva Open University are stated below:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "A copy of your O’Level result\n" +
                        "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Economics",
                    "Two other subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes in Economics, and any one of Accounting, Business Studies/Management, Government, Geography, Statistics, Mathematics, Physics, Chemistry, Agricultural Science or History.",
                        "Lower credit pass in ND/NCE in related fields.",
                        "Very good passes in three (3) JUPEB subjects: Economics, Mathematics and any one (1) of Financial Accounting, Business Management, Government, Physics, Chemistry, Biology and Geography.",
                        "Complete pass in the Intermediate ICAN examination or equivalent (AAT, ACCA, ATS final certificate).",
                        "Foundation of professional examinations such as ICAN, ACCA, ICMA & CIBN.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
                informationIntro = "",
                informationBulletPoints = emptyList(),
                informationEnd = ""
            ),
        ),
    ),
    "BSc. Accounting" to ProgrammeRequirementsInfo(
        description = "The entry requirements for a bachelor’s degree in Economics at Miva Open University are stated below:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Economics",
                    "Two other subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                informationIntro = "",
                informationEnd = "",
                informationBulletPoints = emptyList(),
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes chosen from Accounting, Economics, Business Studies/Management, Government and Geography.",
                        "Two (2) IJMB passes, one (1) of which must be in Accounting, Economics or Business Management.",
                        "Complete pass in the Intermediate ICAN examination or equivalent (AAT, ACCA, ATS final certificate).",
                        "Lower credit pass in ND/NCE in related fields.",
                        "Very good passes in three (3) JUPEB subjects: Economics, and any two (2) of Financial Accounting, Business Studies/Management, Government and Geography.",
                        "Pass in the final exams of the Chartered Institute of Secretaries.",
                    ),
            ),
        ),
    ),
    "BSc. Public Policy and Administration" to ProgrammeRequirementsInfo(
        description = "The entry requirements for a bachelor’s degree in Public Policy and Administration at Miva Open University are stated below:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Economics, History, Civic Education, or Government",
                    "Two other subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Direct Entry Candidates must meet ‘O’ Level requirements for the programme:",
                informationIntro = "",
                informationBulletPoints = emptyList(),
                informationEnd = "",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes chosen from Government, Economics, Accounts, Business Management, Geography or Statistics.",
                        "Lower credit pass in ND/NCE in related fields.",
                        "Very good passes in three (3) JUPEB subjects: Economics, and any two (2) of Financial Accounting, Business Management, Government and Geography.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
            ),
        ),
    ),
    "BSc. Entrepreneurship" to ProgrammeRequirementsInfo(
        description = "The entry requirements for a bachelor’s degree in Entrepreneurship at Miva Open University are stated below:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "Entry Requirements for BSc. in Entrepreneurship",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Economics",
                    "Two other subjects"
                )
            ),
        ),
    ),
    "BSc. Criminology and Security Studies" to ProgrammeRequirementsInfo(
        description = "The entry requirements for a bachelor’s degree in Criminology and Security Studies at Miva Open University are stated below:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "Entry Requirements for BSc. in Criminology and Security Studies",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Economics",
                    "Two other subjects"
                )
            ),
        ),
    ),
    "BSc. Mass Communication and Media Studies" to ProgrammeRequirementsInfo(
        description = "The entry requirements for a bachelor’s degree in Mass Communication and Media Studies at Miva Open University are stated below:",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements ",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Any three Arts or Social Science subjects"
                )
            ),
            RequirementsInfo(
                title = "Direct Entry Admission Requirements",
                subTitle = "Here’s what you need to study for a bachelor’s programme at Miva Open University",
                informationIntro = "A copy of any of the following results:",
                informationBulletPoints = emptyList(),
                informationEnd = "",
                directEntryBulletPoints =
                    listOf(
                        "Two (2) 'A' Level passes chosen from Government, Economics, Accounts, Business Management, Geography or Statistics.",
                        "Lower credit pass in ND/NCE in related fields.",
                        "Very good passes in three (3) JUPEB subjects: Economics, and any two (2) of Financial Accounting, Business Management, Government and Geography.",
                        "International Baccalaureate (IB) Diploma in relevant subjects.",
                    ),
            ),
        ),
    ),
    "BSc. Nursing Science" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva Open University. NOTE: Admission for B.N.Sc. Nursing Science is only available for 200 level.",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "200 Level Entry Requirements ",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Biology",
                    "Physics",
                    "Chemistry",
                    "Registered Nurse Certificate (with Nursing & Midwifery Council of Nigeria)"
                ),
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
            ),
        ),
    ),
    "B.NSc. Nursing Science" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva Open University. NOTE: Admission for B.N.Sc. Nursing Science is only available for 200 level.",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "200 Level Entry Requirements ",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Biology",
                    "Physics",
                    "Chemistry",
                    "Registered Nurse Certificate (with Nursing & Midwifery Council of Nigeria)"
                ),
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
            ),
        ),
    ),
    "B.N.Sc. Nursing Science" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva Open University. NOTE: Admission for B.N.Sc. Nursing Science is only available for 200 level.",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "200 Level Entry Requirements ",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Biology",
                    "Physics",
                    "Chemistry",
                    "Registered Nurse Certificate (with Nursing & Midwifery Council of Nigeria)"
                ),
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
            ),
        ),
    ),
    "BSc. Public Health" to ProgrammeRequirementsInfo(
        description = "Here’s what you need to study for a bachelor’s programme at Miva University",
        requirementsInfo = listOf(
            RequirementsInfo(
                title = "100 Level Entry Requirements",
                subTitle = "A copy of your O’Level result",
                directEntryBulletPoints = emptyList(),
                informationIntro = "The result must include a minimum of five credits in the following subjects in not more than two sittings:",
                informationBulletPoints = listOf(
                    "English Language",
                    "Mathematics",
                    "Biology",
                    "Physics",
                    "Chemistry",
                ),
                informationEnd = "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the provided results will be thoroughly verified for authenticity and compliance with the stated criteria, including JAMB Registration.",
            ),
        ),
    ),
)

val curriculumDescriptionMap = mapOf(
    "Master of Business Administration (MBA)" to "The Master of Business Administration (MBA) curriculum equips you with the knowledge and critical thinking needed for today’s business landscape. Combining theoretical foundations with practical application, you’ll explore core disciplines like accounting, finance, marketing, and operations management. Elective options allow you to personalise your learning, while real-world projects prepare you for leadership roles in any organisation.\n" +
            "\n" +
            "MBA - Management\n" +
            "MBA - Finance\n" +
            "MBA - Entrepreneurship and Innovation\n" +
            "MBA - Marketing and Strategy",

    "Master of Public Administration (MPA)" to "The Master of Public Administration (MPA) at Miva Open University (MOU) is offered by the Department of Public Policy and Administration, SMSS. This interdisciplinary programme equips students with professional expertise and a strong intellectual foundation. Designed for those seeking advanced knowledge through research, it prepares graduates to analyse societal issues and develop solutions. The curriculum fosters interdisciplinary thinking and career opportunities in government, multilateral institutions, and non-profits.",

    "Master of Information Technology (MIT)" to "The Master of Information Technology (MIT) programme equips you with advanced technical skills and strategic knowledge to thrive in the tech industry. Blending theoretical principles with hands-on experience, you’ll explore core areas such as artificial intelligence, cybersecurity, information systems, and software engineering. Elective courses allow you to tailor your learning to your career goals, while real-world projects enhance your problem-solving and leadership skills.You can specialise in any one of these areas:\n\n" +
            "• Artificial Intelligence\n" +
            "• Cybersecurity\n" +
            "• Software Engineering\n" +
            "• Information Management System",

    "Master of Public Health (MPH)" to "The Master of Public Health (MPH) programme equips professionals with the expertise to address pressing public health issues. Designed to build analytical and leadership skills, it provides advanced training in disease prevention, health promotion, and policy development. Through a combination of research, practical application, and interdisciplinary learning, you’ll be prepared to drive impactful health initiatives. You can specialise in:\n" +
            "\n" +
            "MPH - Epidemiology\n" +
            "MPH - Health Education & Health Promotion\n" +
            "MPH - Environmental Health\n" +
            "MPH - Health Policy & Management",

    "BSc. Computer Science" to "Our curriculum is designed to provide students with the skills and knowledge they need to succeed in a variety of careers in the tech industry. The programme covers a wide range of topics, including programming, data structures, algorithms, operating systems, and artificial intelligence.\n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs, a career centre, and a variety of student organisations.",

    "BSc. Cybersecurity" to "Our curriculum is designed to provide students with the skills and knowledge they need to succeed in a variety of careers in the tech industry. The programme covers a wide range of topics, including programming, data structures, algorithms, operating systems, and artificial intelligence.\n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs, a career centre, and a variety of student organisations.",

    "BSc. Data Science" to "Our curriculum is designed to provide students with the skills and knowledge they need to succeed in a variety of careers in the tech industry. The programme covers a wide range of topics, including programming, data structures, algorithms, operating systems, and artificial intelligence.\n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs, a career centre, and a variety of student organisations.",

    "BSc. Software Engineering" to "Our curriculum is designed to provide students with the skills and knowledge they need to succeed in a variety of careers in the tech industry. The programme covers a wide range of topics, including programming, data structures, algorithms, operating systems, and artificial intelligence.\n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs, a career centre, and a variety of student organisations.",

    "BSc. Information Technology" to "Our curriculum is designed to provide students with the skills and knowledge they need to succeed in a variety of careers in the tech industry. The programme covers a wide range of topics, including programming, data structures, algorithms, operating systems, and artificial intelligence.\n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs, a career centre, and a variety of student organisations.",

    "BSc. Business Management" to "Our curriculum is designed to provide students with the necessary competencies and insights crucial for success across a spectrum of roles in business management. The course encapsulates a comprehensive range of subjects, including strategic planning, operations management, marketing strategies, financial analysis, human resources, and leadership development.",

    "BSc. Economics" to "Our programme offers a comprehensive curriculum that covers a wide range of topics relevant to today’s economy. From micro and macroeconomics to econometrics, financial markets, and international trade, you will gain a solid foundation in economic theory and practical skills.\n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs, a career centre, and a variety of student organisations.",

    "BSc. Accounting" to "Our curriculum is designed to provide students with the skills and knowledge they need to succeed in a variety of careers in the accounting and finance industries. The programme covers a wide range of topics, including financial accounting, managerial accounting, taxation, auditing, and accounting information systems.  \n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs for accounting stimulation, advanced software programs, a career centre, professional workshops, and a variety of student organisations.",

    "BSc. Public Policy and Administration" to "Our Public Policy and Administration curriculum is carefully crafted to equip students with the skills and knowledge necessary for success in the field. The programme covers diverse topics, providing a comprehensive understanding of public policy analysis, development, implementation, and evaluation.\n\n" +
            "The faculty is available to students through forums, email, and phone calls. Students also have access to a variety of resources, including a state-of-the-art e-library, virtual computer labs, a career centre, and a variety of student organisations.",

    "BSc. Entrepreneurship" to "Our curriculum is designed to provide students with the necessary competencies and insights crucial for success across a spectrum of roles in Entrepreneurship. The course encapsulates a comprehensive range of subjects, including strategic planning, operations management, marketing strategies, financial analysis, human resources, and leadership development.",

    "BSc. Criminology and Security Studies" to "The Criminology and Security Studies (CSS) programme investigates the basis of social order by drawing on and integrating theories and methods for explaining human behaviours in several social science disciplines such as sociology, psychology, political science, economics, and philosophy.",

    "BSc. Mass Communication and Media Studies" to "Our curriculum is designed to empower you to become a skilled and adaptable communicator in today’s ever-evolving media landscape. We go beyond traditional textbooks to offer an engaging and practical learning experience, with a comprehensive range of subjects, including media writing and storytelling, media production and editing, media law and ethics, digital media and social strategies, and more.",

    "BSc. Nursing Science" to "The bachelor’s in Nursing programme’s curriculum has been meticulously crafted to equip individuals with the essential knowledge and skills required for a prosperous nursing career within the dynamic healthcare environment.",

    "B.NSc. Nursing Science" to "The bachelor’s in Nursing programme’s curriculum has been meticulously crafted to equip individuals with the essential knowledge and skills required for a prosperous nursing career within the dynamic healthcare environment.",

    "B.N.Sc. Nursing Science" to "The bachelor’s in Nursing programme’s curriculum has been meticulously crafted to equip individuals with the essential knowledge and skills required for a prosperous nursing career within the dynamic healthcare environment.",

    "BSc. Public Health" to "The bachelor’s in Public Health program’s curriculum has been meticulously crafted to equip individuals with the essential knowledge and skills required for a prosperous public health career within the dynamic healthcare environment.",
)

val modeOfStudyMap = mapOf(
    "Master of Business Administration (MBA)" to "Flexible",

    "Master of Public Administration (MPA)" to "Blended Learning",

    "Master of Information Technology (MIT)" to "Blended Learning",

    "Master of Public Health (MPH)" to "Blended Learning",

    "BSc. Computer Science" to "Blended Learning",

    "BSc. Cybersecurity" to "Blended Learning",

    "BSc. Data Science" to "Blended Learning",

    "BSc. Software Engineering" to "Blended Learning",

    "BSc. Information Technology" to "Blended Learning",

    "BSc. Business Management" to "Blended Learning",

    "BSc. Economics" to "Blended Learning",

    "BSc. Accounting" to "Blended Learning",

    "BSc. Public Policy and Administration" to "Blended Learning",

    "BSc. Entrepreneurship" to "Blended Learning",

    "BSc. Criminology and Security Studies" to "Blended Learning",

    "BSc. Mass Communication and Media Studies" to "Blended Learning",

    "BSc. Nursing Science" to "Blended Learning",

    "B.NSc. Nursing Science" to "Blended Learning",

    "B.N.Sc. Nursing Science" to "Blended Learning",

    "BSc. Public Health" to "Blended Learning",
)

val postGradsProgrammeLogoMap = mapOf(
    //Postgraduates only
    "Master of Business Administration (MBA)" to DesignSystemR.drawable.ic_mba_logo,
    "Master of Public Administration (MPA)" to DesignSystemR.drawable.ic_mpa_logo,
    "Master of Information Technology (MIT)" to DesignSystemR.drawable.ic_mit_logo,
    "Master of Public Health (MPH)" to DesignSystemR.drawable.ic_mph_logo,
)

@Serializable
data class FacultyInfo(
    val facultyId: String,
    val name: String? = "",
    val description: String? = "",
    val image: String? = "",
    val pageImage: Int? = null,
    val logo: Int? = null,
    val shortName: String? = "",
    val createdAt: String,
    val updatedAt: String,
    val averageTuitionInNaira: Double? = 0.0,
    val averageTuitionInDollar: Double? = 0.0,
    val totalProgrammes: Int? = 0,
)

@Serializable
data class ProgrammeInfo(
    val programmeId: String,
    val name: String,
    val image: String? = "",
    val tuitionDescription: String? = "",
    val description: String? = "",
    val type: String? = "",
    val programmeCode: String? = "",
    val shortName: String? = "",
    val facultyId: String,
    val facultyName: String? = "",
    val status: String? = "",
    val duration: Int? = 0,
    val programmeUnit: Int? = 0,
    val tuitionFeePerSemesterInNaira: Double? = 0.0,
    val tuitionFeePerSemesterInDollar: Double? = 0.0,
    val yearlyTuitionInNaira: Double? = 0.0,
    val yearlyTuitionInDollar: Double? = 0.0,
    val createdAt: String? = "",
    val updatedAt: String? = "",
    val numberOfSemesters: Int? = 0,
    val discountApplied: Double? = 0.0,
    val modeOfStudy: String? = "",
    val curriculumDescription: String? = "",
    val programmeRequirementsInfo: ProgrammeRequirementsInfo? = null,
)

@Serializable
data class CurriculumLevelInfo(
    val level: String,
    val semesters: List<CurriculumSemesterInfo> = emptyList(),
)

@Serializable
data class CurriculumSemesterInfo(
    val semester: String,
    val courses: List<CurriculumCourseInfo> = emptyList(),
)

@Serializable
data class CurriculumCourseInfo(
    val courseId: String,
    val courseName: String? = "",
    val courseCode: String? = "",
    val creditUnit: Int? = 0,
    val courseType: String? = "",
)

@Serializable
data class ProgrammeRequirementsInfo(
    val description: String,
    val requirementsInfo: List<RequirementsInfo>,
)

@Serializable
data class RequirementsInfo(
    val title: String,
    val subTitle: String,
    val informationIntro: String,
    val informationBulletPoints: List<String>,
    val informationEnd: String,
    val otherInformation: String? = "",
    val directEntryBulletPoints: List<String>,
)

data class FacultyDescription(
    val facultyId: String,
    val description: String,
)

val facultyDescription = listOf(
    FacultyDescription(
        facultyId = "f7947d50-da7a-3436-93a5-92b4db43b0a1",
        description = "We are committed to providing accessible, high-quality education that prepares students to excel in the computing and technology fields. Our Cybersecurity, Computer Science, Cloud Computing, and Data Science departments offer a wide range of undergraduate programmes that equip students with the skills and knowledge they need to succeed.\n" +
                "\n" +
                "Our faculty is comprised of highly qualified and experienced professionals who provide rigorous, challenging, and engaging learning experiences for our students. With flexible schedules and course delivery, our programmes enable students to balance their studies with other commitments.\n" +
                "\n" +
                "At Miva Open University, we believe that computing and technology are critical drivers of innovation and growth in today’s economy. Our School of Computing is dedicated to helping our students succeed in the digital marketplace by providing practical training and education. We offer a range of extracurricular activities, internships, and networking events to help students gain practical experience and connect with the community."
    ),

    FacultyDescription(
        facultyId = "b523ff8d-1ced-36ce-b9c8-6492e790c2fb",
        description = "The School of Communication and Media Studies at Miva Open University is dedicated to shaping skilled, innovative, and ethical media professionals.\n" +
                "\n" +
                "With a curriculum designed to meet the demands of today’s digital and information-driven world, our students are equipped with the knowledge and practical skills required to excel in journalism, broadcasting, digital media, and corporate communication.\n" +
                "\n" +
                "Our expert faculty members are at the forefront of media research and practice, ensuring you receive a top-notch education guided by industry trends and global standards.\n" +
                "\n" +
                "Also, we believe learning should go beyond textbooks. That’s why we offer a blend of academic knowledge and real-world experience through practical projects, industry collaborations, and networking opportunities.\n" +
                "Whether you aspire to be a journalist, media strategist, content creator, or public relations expert, our programme is designed to prepare you for a dynamic and impactful career."
    ),
    FacultyDescription(
        facultyId = "0b24df25-fe62-3797-b3a5-0ae0724d2730",
        description = "Our School of Management and Social Sciences houses a variety of programmes, including Business Management, Economics, Accounting, and Public Policy and Administration. Our expert faculty is at the forefront of research in their respective fields, ensuring that you receive a top-notch education from leading experts.\n" +
                "\n" +
                "We don’t believe that learning should be confined to the classroom. That’s why we offer various extracurricular activities, internships, and networking opportunities to give you hands-on experience and connect you with the community. Our aim is to prepare you for a successful career by fostering critical thinking, creativity, and innovation.\n" +
                "\n" +
                "We take pride in our mission to provide education that will equip you with the skills and knowledge needed to excel in your field. So, why not join us and take the first step towards achieving your goals in the digital marketplace."
    ),
    FacultyDescription(
        facultyId = "8b9af1f7-f76d-3f0f-82bd-9c48c4a2e3d0",
        description = "Our School of Allied Health Sciences offers bachelor’s degree programmes in Nursing, Public Health.\n" +
                "\n" +
                "Our highly qualified and experienced faculty provide rigorous, challenging, and engaging learning experiences for our students. Flexible schedules and course delivery make it easy to balance your studies with other commitments.\n" +
                "\n" +
                "We believe that learning should extend beyond the classroom, so we offer a variety of extracurricular activities, internships, and networking opportunities to give you hands-on experience and connect you with the community.\n" +
                "\n" +
                "We take pride in our mission to provide education that will equip you with the skills and knowledge needed to excel in your field of choice. Join us and take the first step towards achieving your goals in the Healthcare industry."
    )

)