package org.yinwang.rubysonar.ast;

import org.jetbrains.annotations.NotNull;
import org.yinwang.rubysonar.State;
import org.yinwang.rubysonar._;
import org.yinwang.rubysonar.types.ModuleType;
import org.yinwang.rubysonar.types.Type;


public class Module extends Node {

    public Node locator;
    public Name name;
    public Block body;


    public Module(Node locator, Block body, int start, int end) {
        super(start, end);
        this.locator = locator;
        this.body = body;
        if (locator instanceof Attribute) {
            this.name = ((Attribute) locator).attr;
        } else if (locator instanceof Name) {
            this.name = (Name) locator;
        } else {
            _.die("illegal module locator: " + locator);
        }
        addChildren(locator, body);
    }


    @NotNull
    @Override
    public Type transform(@NotNull State s) {
        ModuleType mt = s.lookupOrCreateModule(locator, file);
        transformExpr(body, mt.getTable());
        return mt;
    }


    @NotNull
    @Override
    public String toString() {
        return "(module:" + locator + ")";
    }

}