
a0 = [x*x | x <- [1..8]]
a1 = [x*x | x <- [1..8], mod x 2 == 0]

a2 = [[x, y] | x <- [1..8], y <- [1..3]]


length' xs = sum [1 | _ <- xs]

-- tuples
t0 = (1, 'C', Just 3.4)


qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (x:xs) = (qsort [n | n <- xs, x >= n]) ++ [x] ++ (qsort [n | n <- xs, x < n])

_ = foldl (\b a -> b + snd a) 0 [('c', 2), ('b', 3)]

map' :: (a -> b) -> [a] -> [b]
map' f xs = foldr (\b a -> (f b) : a) [] xs

-- ej: [a,b,c,d] ~> (a - b + c - d)
_ = foldr (\b a -> b - a) 0 [1,3,4,5]


-- data Either a b = Left a | Right b deriving (Eq, Ord, Read, Show)
ff :: Either String Integer -> String
ff (Left a) = "Left: " ++ a
ff (Right b) = "Right + 1: " ++ show (b + 1)


infixr 5 :<
data List a = Nil | a :< (List a) deriving (Show)

(.++) ::  List a -> List a -> List a
(.++) Nil ys = ys
(.++) (x :< xs) ys = x :< (xs .++ ys)


data BST a = EmptyBST | Node a (BST a) (BST a) deriving (Eq)

singleNodeBST :: (Ord a, Eq a) => a -> BST a
singleNodeBST a = Node a EmptyBST EmptyBST

insertBST :: (Ord a, Eq a) => BST a -> a -> BST a
insertBST EmptyBST a = singleNodeBST a
insertBST (Node r left right) a
    | r == a = Node r left right
    | r > a = Node r (insertBST left a) right
    | r < a = Node r left (insertBST right a)


instance (Show a, Eq a) => Show (BST a) where
    show EmptyBST = "Nil"
    show a = showHelper a "" False False False


type Prefix = String

showHelper :: (Show a, Eq a) => BST a -> Prefix -> Bool -> Bool -> Bool -> String
showHelper EmptyBST _ _ _ _ = ""
showHelper tree@(Node n l r) pre isRight prevHasLeft prevHasRight
    | isRight == True = pre ++ "├── " ++ show n ++ "\n" ++ (showHelper r (pre ++ pippe) True hasL hasR) ++ (showHelper l (pre ++ "    ") False hasL hasR)
    | isRight == False = pre ++ "└── " ++ show n ++ "\n" ++ (showHelper r (pre ++ pippe) True hasL hasR) ++ (showHelper l (pre ++ "    ") False hasL hasR)
    where
        hasL = if (l /= EmptyBST) then True else False
        hasR = if (r /= EmptyBST) then True else False
        pippe = if prevHasLeft && prevHasRight then "│   " else "    "


class Tofu t where
    tofu :: j a -> t a j

data Ddd a b = Kkk (b a) deriving (Show)

instance Tofu Ddd where
    tofu x = Kkk x
