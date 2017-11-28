package java.pattern;
interface Person {
	void accpect(Visitor v);
}

class Student implements Person {

	public void study() {
		System.out.println("study");
	}

	@Override
	public void accpect(Visitor v) {
		v.visit(this);
	}
}

class Teacher implements Person {

	public void teach() {
		System.out.println("teach");
	}

	@Override
	public void accpect(Visitor v) {
		v.visit(this);
	}
}

interface Visitor {
	void visit(Student s);

	void visit(Teacher t);
}

public class VisitorPattern {

	public static void main(String[] args) {
		Person[] es = new Person[] { new Student(), new Teacher() };

		Visitor v = new Visitor() {

			@Override
			public void visit(Teacher t) {
				t.teach();
			}

			@Override
			public void visit(Student s) {
				s.study();
			}
		};
		for (Person e : es) {
			e.accpect(v);
		}
	}
}
